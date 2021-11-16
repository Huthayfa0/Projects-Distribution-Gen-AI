import random,ProjectsManager,StudentGroup

class Population:
    def __init__(self, distributionManager, populationSize, initialise,students):
        self.students=students
        self.distributions = []
        for i in range(0, populationSize):
            self.distributions.append(None)

        if initialise:
            for i in range(0, populationSize):
                newDistribution = ProjectsManager.Distribution(distributionManager)
                newDistribution.generateIndividual()
                self.saveDistribution(i, newDistribution)

    def __setitem__(self, key, value):
        self.distributions[key] = value

    def __getitem__(self, index):
        return self.distributions[index]

    def saveDistribution(self, index, tour):
        self.distributions[index] = tour

    def getDistribution(self, index):
        return self.distributions[index]

    def getFittest(self):
        fittest = self.distributions[0]
        for i in range(0, self.populationSize()):
            if fittest.getFitness(self.students) <= self.getDistribution(i).getFitness(self.students):
                fittest = self.getDistribution(i)
        return fittest

    def populationSize(self):
        return len(self.distributions)


class GA:
    def __init__(self, distributionManager,students):
        self.students=students
        self.distributionManager = distributionManager
        self.mutationRate = 0.05
        self.tournamentSize = 20
        self.elitism = True

    def evolvePopulation(self, pop):
        newPopulation = Population(self.distributionManager, pop.populationSize(), False, self.students)
        elitismOffset = 0
        if self.elitism:
            newPopulation.saveDistribution(0, pop.getFittest())
            elitismOffset = 1

        for i in range(elitismOffset, newPopulation.populationSize()):
            parent1 = self.tournamentSelection(pop)
            parent2 = self.tournamentSelection(pop)
            child = self.crossover(parent1, parent2)
            newPopulation.saveDistribution(i, child)

        for i in range(elitismOffset, newPopulation.populationSize()):
            self.mutate(newPopulation.getDistribution(i))

        return newPopulation

    def crossover(self, parent1, parent2):
        child = ProjectsManager.Distribution(self.distributionManager)

        startPos = int(random.random() * parent1.distributionSize())
        endPos = int(random.random() * parent1.distributionSize())
        if(endPos<startPos):
            startPos,endPos=endPos,startPos
        for i in range(0, child.distributionSize()):
            if startPos < i < endPos:
                child.setProject(i, parent1.getProject(i))
        for i in range(0, parent2.distributionSize()):
            if not child.containsProject(parent2.getProject(i)):
                for ii in range(0, child.distributionSize()):
                    if child.getProject(ii) is None:
                        child.setProject(ii, parent2.getProject(i))
                        break
        return child

    def mutate(self, distribution):
        for distributionPos1 in range(0, distribution.distributionSize()):
            if random.random() < self.mutationRate:
                distributionPos2 = int(distribution.distributionSize() * random.random())

                project1 = distribution.getProject(distributionPos1)
                project2 = distribution.getProject(distributionPos2)

                distribution.setProject(distributionPos2, project1)
                distribution.setProject(distributionPos1, project2)

    def tournamentSelection(self, pop):
        tournament = Population(self.distributionManager, self.tournamentSize, False, self.students)
        for i in range(0, self.tournamentSize):
            randomId = int(random.random() * pop.populationSize())
            tournament.saveDistribution(i, pop.getDistribution(randomId))
        fittest = tournament.getFittest()
        return fittest


