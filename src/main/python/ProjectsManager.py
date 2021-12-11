from tabula import read_pdf
import pandas as pd
import random


class Project:
    def __init__(self, pid, supervisor, title, description, analysis=None):
        self.id = int(pid)
        self.supervisor = supervisor
        self.title=title
        self.description=description
        self.analysis = analysis
        self.correlations={}

    def getID(self):
        return self.id

    def __repr__(self):
        return 'id=' + str(self.id)

    def correlation(self,project):
        if project.id in self.correlations:
            return self.correlations[project.id]
        if self.id==project.id:
            return 1.0
        sum=0.0
        diameter=0.0
        for x in self.analysis['concepts']:
            diameter += x['relevance']
            for y in project.analysis['concepts']:
                if x['text']==y['text']:
                    sum+=min(y['relevance'],x['relevance'])
                    break
        if diameter==0.0:
            self.correlations[project.id] = 0.0
            return 0.0
        ans=sum/diameter
        self.correlations[project.id]=ans
        return ans

class DistributionManager:
    projects = []

    def addProject(self, project):
        self.projects.append(project)

    def getProject(self, index):
        return self.projects[index]

    def numberOfProjects(self):
        return len(self.projects)


class Distribution:
    def __init__(self, distributionManager, distribution=None):
        self.distributionManager = distributionManager
        self.distribution = []
        self.fitness = -1
        if distribution is not None:
            self.distribution = distribution
        else:
            for i in range(0, self.distributionManager.numberOfProjects()):
                self.distribution.append(None)

    def __len__(self):
        return len(self.distribution)

    def __getitem__(self, index):
        return self.distribution[index]

    def __setitem__(self, key, value):
        self.distribution[key] = value

    def __repr__(self):
        geneString = "|"
        for i in range(0, self.distributionSize()):
            geneString += str(self.getProject(i)) + "|"
        return geneString

    def generateIndividual(self):
        for projectIndex in range(0, self.distributionManager.numberOfProjects()):
            self.setProject(projectIndex, self.distributionManager.getProject(projectIndex))
        random.shuffle(self.distribution)

    def generateIndividualUsingCSP(self, students):
        x = [p for p in self.distributionManager.projects]
        l=list(range(0, len(students)))
        random.shuffle(l)
        for projectIndex in l:
            v = False
            for c in students[projectIndex].choices:
                if (c in x):
                    self.setProject(projectIndex, c)
                    x.remove(c)
                    v = True
                    break
            if not v:
                i = random.randrange(0,len(x))
                self.setProject(projectIndex, x[i])
                x.remove(x[i])
        for projectIndex in range(len(students), self.distributionManager.numberOfProjects()):
            i = random.randrange(0,len(x))
            self.setProject(projectIndex, x[i])
            x.remove(x[i])

    def getProject(self, projectIndex):
        return self.distribution[projectIndex]

    def setProject(self, projectIndex, project):
        self.distribution[projectIndex] = project
        self.fitness = -1

    def getFitness(self, students):
        if self.fitness == -1:
            fitnesses = []
            for i in range(len(students)):
                fitnesses.append(students[i].calculateCompatibility(self.getProject(i)))
            self.fitness = sum(fitnesses)

        return self.fitness


    def distributionSize(self):
        return len(self.distribution)

    def containsProject(self, project):
        return project in self.distribution


def read_file(fileName):

    distributionManager = DistributionManager()
    if fileName.endswith('pdf'):
        project = None
        for df in read_pdf(fileName, pages="all"):
            row = df.columns.tolist()
            if row[0].isnumeric():
                if not pd.isnull(float(row[0])):
                    if project is not None:
                        distributionManager.addProject(project)
                    project = Project(float(row[0]), "", "", "")
                if not pd.isnull(row[1]):
                    project.supervisor += row[1]+"\n"
                if not pd.isnull(row[2]):
                    project.title += row[2]+"\n"
                if not pd.isnull(row[3]):
                    project.description += row[3]+"\n"
            for row in df.values.tolist():
                if not pd.isnull(row[0]):
                    if project is not None:
                        distributionManager.addProject(project)
                    project = Project(row[0], "", "", "")
                if not pd.isnull(row[1]):
                    project.supervisor += row[1]+"\n"
                if not pd.isnull(row[2]):
                    project.title += row[2]+"\n"
                if not pd.isnull(row[3]):
                    project.description += row[3]+"\n"
        if project is not None:
            distributionManager.addProject(project)
    elif fileName.endswith('json'):
        import json
        l=json.load(open(fileName))
        for x in l:
            distributionManager.addProject(Project(x["id"],x["supervisor"],x["title"],x["description"],x["analysis"] if "analysis" in x else None))
    else:
        raise NotImplementedError

    from pathlib import Path
    from dotenv import load_dotenv
    import requests, os, json
    from requests.auth import HTTPBasicAuth

    load_dotenv(dotenv_path=Path('ibm-credentials.env'))
    API_KEY = os.getenv('NATURAL_LANGUAGE_UNDERSTANDING_APIKEY')
    API_URL = os.getenv('NATURAL_LANGUAGE_UNDERSTANDING_URL')
    url = f'{API_URL}/v1/analyze?version=2021-08-01'
    payload = json.load(open("src/main/resources/com/main/ai1/json/request.json"))
    auth = HTTPBasicAuth('apikey', API_KEY)
    headers = {'content-type': 'application/json', 'Accept-Charset': 'UTF-8'}
    for x in distributionManager.projects:
        if x.analysis is None or 'error' in x.analysis:
            payload["text"]= x.description if x.description else x.title
            r=requests.post(url, json=payload, auth=auth, headers=headers)
            x.analysis = r.json()
    return distributionManager
