class StudentGroup:
    def __init__(self, names, choices):
        self.names = sorted(names)
        self.choices = choices

    def getNames(self):
        return self.names

    def getChoices(self):
        return self.choices

    def calculateCompatibility(self, project):
        ans = 0
        for i in range(len(self.choices)):
            ans+=((10 - i)*10)*self.choices[i].correlation(project) # 10 for first, 9 for second, 8 for third
        return ans/2.7

    def __repr__(self):
        return ",".join(self.names) + " chose: " + ",".join(self.choices)


def read_file(fileName, distributionManager):
    groups = []
    # TODO might edit to include more file types
    if fileName.endswith('csv'):
        with open(fileName) as file:
            next(file)
            for line in file:
                names = []
                line = line.replace('"', '')
                strings = line.split(",")
                strings = list(filter(None, map(lambda x: x.strip(' \n'), strings)))
                while not strings[0].isnumeric():
                    names.append(strings.pop(0))
                choices = list(map(lambda x: list(filter(lambda c: c.getID() == x, distributionManager.projects))[0],
                                   map(int, strings)))
                groups.append(StudentGroup(names, choices))
    else:
        raise NotImplementedError
    return groups
