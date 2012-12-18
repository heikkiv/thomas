def file = new File('wirc2.log')
def outputFile = new File('data.txt')
outputFile.write('')

file.eachLine { line ->
    def parts = line.split('\t')
    def message = parts[3..<parts.length].join(' ')
    outputFile << message + '\n'
}
