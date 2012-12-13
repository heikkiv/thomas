package com.heikkiv.ml.thomas

import org.junit.Test
import static org.junit.Assert.assertEquals

class NaiveBayesClassifierTest {

    @Test
    void probability() {
        NaiveBayesClassifier cl = new NaiveBayesClassifier()
        cl.sampleTrain()
        assertEquals(0.156, cl.probability('good', 'quick rabbit'), 0.001)
        assertEquals(0.05, cl.probability('bad', 'quick rabbit'), 0.001)
    }

    @Test
    void classify() {
        NaiveBayesClassifier cl = new NaiveBayesClassifier()
        cl.sampleTrain()
        assertEquals('good', cl.classify('quick rabbit'))
        assertEquals('bad', cl.classify('quick money'))
        cl.setThreshold('bad', 3)
        assertEquals('unknown', cl.classify('quick money'))

        (1..10).each { cl.sampleTrain() }

        assertEquals('bad', cl.classify('quick money'))
    }
}
