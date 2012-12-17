package com.heikkiv.ml.thomas

class BayesClassifier {

    def assumedProbability = 0.5 // aka. Prior probability
    def weight = 1

    def featureCounts = [:]
    def categoryCounts = [:]

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
        categoryCounts.keySet().each { category ->
            n += getFeatureCountInCategory(feature, category)
        }
        return n
    }

    public int getFeatureCountInCategory(String feature, String category) {
        if (featureCounts[feature]) {
            if (featureCounts[feature][category]) {
                return featureCounts[feature][category]
            } else {
                return 0
            }
        } else {
            return 0
        }
    }

    /**
     * Number of items in given category
     *
     * @param category
     * @return
     */
    public int getItemCountInCategory(String category) {
        if (categoryCounts[category]) {
            return categoryCounts[category]
        } else {
            return 0
        }
    }

    public int getTotalItemCount() {
        int n = 0
        categoryCounts.keySet().each { category ->
            n += getItemCountInCategory(category)
        }
        return n
    }

    public void train(String document, String category) {
        def features = getFeatures(document)
        features.each { feature ->
            incrementFeatureCount(feature, category)
        }
        incrementClassificationCount(category)
    }

    protected List<String> getFeatures(String document) {
        return document.split() as List;
    }

    protected void incrementFeatureCount(String feature, String category) {
        if (!featureCounts[feature]) {
            featureCounts[feature] = [:]
        }
        if (featureCounts[feature][category]) {
            featureCounts[feature][category] += 1
        } else {
            featureCounts[feature][category] = 1
        }
    }

    private void incrementClassificationCount(String category) {
        if (categoryCounts[category]) {
            categoryCounts[category] += 1
        } else {
            categoryCounts[category] = 1
        }
    }

    public void sampleTrain() {
        train('Nobody owns the water.','good')
        train('the quick rabbit jumps fences','good')
        train('buy pharmaceuticals now','bad')
        train('make quick money at the online casino','bad')
        train('the quick brown fox jumps','good')
    }

    protected List<String> getCategories() {
        return categoryCounts.keySet() as List;
    }

}
