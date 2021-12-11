import json
import sys

import matplotlib.pyplot as plt
import GeneticsAlgorithem
import ProjectsManager
import StudentGroup

if __name__ == '__main__':

    #sys.exit(1)
    distributionManager = ProjectsManager.read_file("src/main/resources/com/main/ai1/json/Projects.json")
    students = StudentGroup.read_file(
        "src/main/resources/com/main/ai1/StudentsSelections.csv",
        distributionManager)
    f = open("src/main/resources/com/main/ai1/json/Projects.json", "w")
    json.dump(distributionManager.projects, f, default=lambda o: o.__dict__)
    f.close()
    f = open("src/main/resources/com/main/ai1/json/Students.json", "w")
    json.dump(students, f, default=lambda o: o.__dict__)
    f.close()

    # Initialize population
    pop = GeneticsAlgorithem.Population(distributionManager, 100, True, students)

    # Evolve population for 200 generations
    progress = [pop.getFittest().getFitness(students)]
    ga = GeneticsAlgorithem.GA(distributionManager, students)
    for i in range(200):
        pop = ga.evolvePopulation(pop)
        progress.append(pop.getFittest().getFitness(students))
    
    plt.plot(progress)
    plt.ylabel('Fittness')
    plt.xlabel('Generation')
    plt.savefig("src/main/resources/com/main/ai1/images/Output.png")
    # Print final results
    f = open("src/main/resources/com/main/ai1/json/newOutput.json", "w")
    json.dump(pop.getFittest(), f, default=lambda o: o.__dict__)
    f.close()
    f = open("src/main/resources/com/main/ai1/json/Output.json")
    dist=json.load(f)
    f.close()
    an=ProjectsManager.Distribution(None,[])
    for x in dist['distribution']:
        an.distribution.append(ProjectsManager.Project(x["id"], x["supervisor"], x["title"], x["description"], x["analysis"] if "analysis" in x else None))
    if an.getFitness(students)<pop.getFittest().getFitness(students):
        an=pop.getFittest()
    #print(an.getFitness(students))
    f = open("src/main/resources/com/main/ai1/json/Output.json", "w")
    json.dump(an, f, default=lambda o: o.__dict__)
    f.close()
    names=["Project 1", "Project 2", "Project 3"]
    plt.close()
    plt.ylabel('Correlation')
    plt.xlabel('Choices')
    for i in range(len(students)):
        choices=[]
        for pro in students[i].choices:
            choices.append(pro.correlation(an.getProject(i))*100)
        plt.bar(names,choices)
        plt.savefig("src/main/resources/com/main/ai1/images/GroubsFigures/Output{}.png".format(i))
        plt.close()