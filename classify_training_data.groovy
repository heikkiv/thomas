def file = new File('data.txt')
def outputFile = new File('validation_data.txt')

BufferedReader br = new BufferedReader(new InputStreamReader(System.in))

file.eachLine { line ->
    print "$line : "
    def userInput = br.readLine()
    if (userInput == 'q') {
        System.exit(0)
    } else if (userInput != 's') {
        outputFile.append(userInput + '\t' + line + '\n')
    }
}
