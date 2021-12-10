import matplotlib.pyplot as plt
import ProjectsManager,StudentGroup,GeneticsAlgorithem
import sys,json

if __name__ == '__main__':
    distributionManager = ProjectsManager.read_file("src/main/resources/com/main/ai1/f.pdf")
    students = StudentGroup.read_file(
        "src/main/resources/com/main/ai1/StudentsSelections.csv",
        distributionManager)
    f = open("src/main/resources/com/main/ai1/json/Projects.json", "w")
    json.dump(distributionManager.projects,f,default=lambda o: o.__dict__)
    f.close()
    f = open("src/main/resources/com/main/ai1/json/Students.json", "w")
    json.dump(students, f, default=lambda o: o.__dict__)
    f.close()
    #print(json.dumps(distributionManager.projects,default=lambda o: o.__dict__))

    # Initialize population
    pop = GeneticsAlgorithem.Population(distributionManager, 100, True, students)
    print("Initial fittness: " + str(pop.getFittest().getFitness(students)))

    # Evolve population for 200 generations
    progress = [pop.getFittest().getFitness(students)]
    ga = GeneticsAlgorithem.GA(distributionManager, students)
    for i in range(200):
        pop = ga.evolvePopulation(pop)
        progress.append(pop.getFittest().getFitness(students))

    plt.plot(progress)
    plt.ylabel('Fittness')
    plt.xlabel('Generation')
    plt.show()
    # Print final results
    print("Finished")
    print("Final fittness: " + str(pop.getFittest().getFitness(students)))
    print("Solution:")
    print(pop.getFittest())
    print(pop.getFittest().print(students))
    f = open("src/main/resources/com/main/ai1/json/Output.json", "w")
    json.dump(pop.getFittest(),f,default=lambda o: o.__dict__)
    f.close()
