package com.heikkiv.ml.thomas

import com.heikkiv.ml.thomas.mongo.CategoryRepository
import com.heikkiv.ml.thomas.mongo.FeatureRepository
import com.heikkiv.ml.thomas.mongo.HashMapCategoryRepository
import com.heikkiv.ml.thomas.mongo.HashMapFeatureRepository

class BayesClassifier {

    def assumedProbability = 0.5 // aka. Prior probability
    def weight = 1

    CategoryRepository categoryRepository = new HashMapCategoryRepository()
    FeatureRepository featureRepository = new HashMapFeatureRepository()

    /**
     * Probability that a document in given category will contain the given feature (word)
     *
     * @param feature
     * @param category
     * @return
     */
    public double getFeatureProbability(String feature, String category) {
        if (getItemCountInCategory(category) == 0) {
            return 0
        } else {
            return getFeatureCountInCategory(feature, category) / getItemCountInCategory(category)
        }
    }

    /**
     * Weighted probability that a document in given category will contain the given feature (word).
     * Takes into account 'prior' knowledge about the frequency of the feature
     *
     * @param feature
     * @param category
     * @return
     */
    public double getWeightedFeatureProbability(String feature, String category) {
        def baseProbability = getFeatureProbability(feature, category)
        def featureCountInAllCategories = getFeatureCountInAllCategories(feature)
        def weightedAverage = ((weight * assumedProbability) + (featureCountInAllCategories * baseProbability)) / (weight + featureCountInAllCategories)
        return weightedAverage
    }

    public int getFeatureCountInAllCategories(String feature) {
        int n = 0
        categoryRepository.getCategories().each { category ->
            n += getFeatureCountInCategory(feature, category)
        }
        return n
    }

    public int getFeatureCountInCategory(String feature, String category) {
        featureRepository.getFeatureCountInCategory(feature, category)
    }

    /**
     * Number of items in given category
     *
     * @param category
     * @return
     */
    public int getItemCountInCategory(String category) {
        categoryRepository.getItemCountInCategory(category)
    }

    public int getTotalItemCount() {
        int n = 0
        categoryRepository.getCategories().each { category ->
            n += getItemCountInCategory(category)
        }
        return n
    }

    public void train(String document, String category) {
        def features = getFeatures(document)
        features.each { feature ->
            incrementFeatureCount(feature, category)
        }
        categoryRepository.incrementClassificationCount(category)
    }

    protected List<String> getFeatures(String document) {
        return document.split() as List;
    }

    protected void incrementFeatureCount(String feature, String category) {
        featureRepository.incrementFeatureCount(feature, category)
    }

    public void sampleTrain() {
        train('Nobody owns the water.','good')
        train('the quick rabbit jumps fences','good')
        train('buy pharmaceuticals now','bad')
        train('make quick money at the online casino','bad')
        train('the quick brown fox jumps','good')
    }

    protected Set<String> getCategories() {
        return categoryRepository.getCategories()
    }

}
