package com.heikkiv.ml.thomas.mongo

class HashMapFeatureRepository implements FeatureRepository {

    def featureCounts = [:]

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
