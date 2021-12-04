import random, numpy as np


class Project:
    # TODO To increase efficiency of class: name, description, etc..
    def __init__(self, pid):
        self.id = pid

    def getID(self):
        return self.id

    def __repr__(self):
        return 'id=' + str(self.id)


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
            self.fitness = np.sum(fitnesses)

        return self.fitness

    def print(self, students):
        fitnesses = []
        for i in range(len(students)):
            fitnesses.append(students[i].calculateCompatibility(self.getProject(i)))
        print("First:", len(list(filter(lambda x: x == 10, fitnesses))))
        print("Second:", len(list(filter(lambda x: x == 9, fitnesses))))
        print("Third:", len(list(filter(lambda x: x == 8, fitnesses))))
        print("Zeros:", len(list(filter(lambda x: x == 0, fitnesses))))

    def distributionSize(self):
        return len(self.distribution)

    def containsProject(self, project):
        return project in self.distribution
