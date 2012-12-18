package com.heikkiv.ml.thomas.training

import com.heikkiv.ml.thomas.Classifier
import com.heikkiv.ml.thomas.NaiveBayesClassifier
import com.heikkiv.ml.thomas.mongo.MongoBayesClassifierRepository

class IrcTrainer {

    Classifier classifier

    IrcTrainer() {
        classifier = new NaiveBayesClassifier()
        classifier.repository = new MongoBayesClassifierRepository()
    }

    void train(File file) {
        file.eachLine {
            def temp = it.split('\t')
            def document = temp[1]
            def category
            if (temp[0] == '1') {
                category = 'work'
            } else {
                category = 'banter'
            }
            println "$category: $document"
            classifier.train(document, category)
        }
    }

}
