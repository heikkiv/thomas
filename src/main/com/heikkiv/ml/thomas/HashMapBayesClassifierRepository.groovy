package com.heikkiv.ml.thomas

class HashMapBayesClassifierRepository implements BayesClassifierRepository {

    def categoryCounts = [:]
    def featureCounts = [:]

    @Override
    Set<String> getCategories() {
        return categoryCounts.keySet()
    }

    @Override
    int getItemCountInCategory(String category) {
        if (categoryCounts[category]) {
            return categoryCounts[category]
        } else {
            return 0
        }
    }

    @Override
    void incrementClassificationCount(String category) {
        if (categoryCounts[category]) {
            categoryCounts[category] += 1
        } else {
            categoryCounts[category] = 1
        }
    }

    @Override
    void incrementFeatureCount(String feature, String category) {
        if (!featureCounts[feature]) {
            featureCounts[feature] = [:]
        }
        if (featureCounts[feature][category]) {
            featureCounts[feature][category] += 1
        } else {
            featureCounts[feature][category] = 1
        }
    }

    @Override
    int getFeatureCountInCategory(String feature, String category) {
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

}
