import com.heikkiv.ml.thomas.NaiveBayesClassifier
import com.heikkiv.ml.thomas.mongo.MongoBayesClassifierRepository

int n = 0
int ok = 0
int unknown = 0
int fail = 0

def classifier = new NaiveBayesClassifier()
classifier.repository = new MongoBayesClassifierRepository()

def input = new File('validation_data.txt')
input.eachLine {
    def temp = it.split('\t')
    def document = temp[1]
    def category
    if (temp[0] == '1') {
        category = 'work'
    } else {
        category = 'banter'
    }
    def response = classifier.classify(document)
    if (response == category) {
        println 'ok'
        ok += 1
    } else if (response == 'unknown') {
        prinln 'unknown'
        unknown += 1
    } else {
        println 'fail'
        fail += 1
    }
    n += 1
}

println "n: $n"
println "ok: $ok"
println "unknown: $unknown"
println "fail: $fail"

println 'DONE'