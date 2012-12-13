package com.heikkiv.ml.thomas

import org.junit.Test
import static org.junit.Assert.assertEquals

class BayesClassifierTest {

    @Test
    void featureAndCategoryCounting() {
        BayesClassifier cl = new BayesClassifier()
        cl.train('the quick brown fox jumps over the lazy dog', 'good')
        cl.train('make quick money in the online casino', 'bad')
        assertEquals(cl.getFeatureCount('the', 'good'), 2)
        assertEquals(cl.getFeatureCount('the','bad'), 1)
        assertEquals(cl.getFeatureCount('make','bad'), 1)
        assertEquals(cl.getFeatureCount('casino','good'), 0)
        assertEquals(cl.getCategoryCount('good'), 1)
        assertEquals(cl.getCategoryCount('bad'), 1)
        assertEquals(cl.getCategoryCount('worse'), 0)
    }

    @Test
    void featureProbability() {
        BayesClassifier cl = new BayesClassifier()
        cl.sampleTrain()
        assertEquals(0.666, cl.getFeatureProbability('quick', 'good'), 0.001)
    }

    @Test
    void weightedFeatureProbability() {
        BayesClassifier cl = new BayesClassifier()
        cl.sampleTrain()
        assertEquals(0.25, cl.getWeightedFeatureProbability('money', 'good'), 0.01)
        cl.sampleTrain()
        assertEquals(0.166, cl.getWeightedFeatureProbability('money', 'good'), 0.001)
    }

    @Test
    void totalItemCount() {
        BayesClassifier cl = new BayesClassifier()
        cl.sampleTrain()
        assertEquals(5, cl.getTotalItemCount())
    }

}
