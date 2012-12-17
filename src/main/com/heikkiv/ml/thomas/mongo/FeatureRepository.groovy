package com.heikkiv.ml.thomas.mongo

public interface FeatureRepository {

    void incrementFeatureCount(String feature, String category)

    int getFeatureCountInCategory(String feature, String category)

}