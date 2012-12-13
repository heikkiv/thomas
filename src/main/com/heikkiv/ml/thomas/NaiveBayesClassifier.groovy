package com.heikkiv.ml.thomas

class NaiveBayesClassifier extends BayesClassifier implements Classifier {

    def thresholds = [:]

    public void setThreshold(String category, double threshold) {
        thresholds[category] = threshold
    }

    public double getThreshold(String category) {
        return (thresholds[category]) ? thresholds[category] : 1
    }

    /**
     * Conditional probability for document given category
     * P( document | category )
     *
     * @param document
     * @param category
     * @return
     */
    public double documentProbability(String document, String category) {
        def features = getFeatures(document)
        double p = 1
        features.each { feature ->
            p *= getWeightedFeatureProbability(feature, category)
        }
        return p
    }

    /**
     * Conditional probability for category given document
     * P( category | document )
     *
     * @param document
     * @param category
     * @return
     */
    public double probability(String category, String document) {
        double categoryProbability = getItemCountInCategory(category) / getTotalItemCount()
        double documentProbability = documentProbability(document, category)
        return documentProbability * categoryProbability
    }

    @Override
    public String classify(String document) {
        def probabilities = [:]

        double max = 0
        String bestCategory = ''
        def categories = getCategories()
        categories.each { category ->
            probabilities[category] = probability(category, document)
            if (probabilities[category] > max) {
                max = probabilities[category] as Double
                bestCategory = category
            }
        }

        probabilities.keySet().each { category ->
            if (category != bestCategory) {
                if (probabilities[category] * getThreshold(bestCategory) > probabilities[bestCategory]) {
                    bestCategory = 'unknown'
                }
            }
        }

        return bestCategory
    }

}
