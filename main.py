import StudentGroup,ProjectsManager,GeneticsAlgorithem, json, matplotlib.pyplot as plt

if __name__ == '__main__':
    students=StudentGroup.read_file("StudentsSelections.csv")
    distributionManager = ProjectsManager.DistributionManager()

    for i in range(1,39):
        distributionManager.addProject(ProjectsManager.Project(i))
    # Initialize population
    pop = GeneticsAlgorithem.Population(distributionManager, 100, True,students)
    print("Initial distance: " + str(pop.getFittest().getFitness(students)))

    # Evolve population for 50 generations
    progress = []
    progress.append(pop.getFittest().getFitness(students))
    ga = GeneticsAlgorithem.GA(distributionManager,students)
    pop = ga.evolvePopulation(pop)
    for i in range(0, 200):
        pop = ga.evolvePopulation(pop)
        progress.append(pop.getFittest().getFitness(students))

    plt.plot(progress)
    plt.ylabel('Distance')
    plt.xlabel('Generation')
    plt.show()
    # Print final results
    print("Finished")
    print("Final distance: " + str(pop.getFittest().getFitness(students)))
    print("Solution:")
    print(pop.getFittest())
    print(pop.getFittest().print(students))
    f=open("Best.json","w")
    f.write(json.dumps(str(pop.getFittest())))
    f.close()