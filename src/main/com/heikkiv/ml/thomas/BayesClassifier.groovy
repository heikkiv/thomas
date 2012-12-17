package com.heikkiv.ml.thomas

class BayesClassifier {

    def assumedProbability = 0.5 // aka. Prior probability
    def weight = 1

    BayesClassifierRepository repository = new HashMapBayesClassifierRepository()

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
        repository.getCategories().each { category ->
            n += getFeatureCountInCategory(feature, category)
        }
        return n
    }

    public int getFeatureCountInCategory(String feature, String category) {
        repository.getFeatureCountInCategory(feature, category)
    }

    /**
     * Number of items in given category
     *
     * @param category
     * @return
     */
    public int getItemCountInCategory(String category) {
        repository.getItemCountInCategory(category)
    }

    public int getTotalItemCount() {
        int n = 0
        repository.getCategories().each { category ->
            n += getItemCountInCategory(category)
        }
        return n
    }

    public void train(String document, String category) {
        def features = getFeatures(document)
        features.each { feature ->
            incrementFeatureCount(feature, category)
        }
        repository.incrementClassificationCount(category)
    }

    protected List<String> getFeatures(String document) {
        return document.split() as List;
    }

    protected void incrementFeatureCount(String feature, String category) {
        repository.incrementFeatureCount(feature, category)
    }

    public void sampleTrain() {
        train('Nobody owns the water.','good')
        train('the quick rabbit jumps fences','good')
        train('buy pharmaceuticals now','bad')
        train('make quick money at the online casino','bad')
        train('the quick brown fox jumps','good')
    }

    protected Set<String> getCategories() {
        return repository.getCategories()
    }

}
