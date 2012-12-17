package com.heikkiv.ml.thomas.mongo

public interface BayesClassifierRepository {

    Set<String> getCategories()

    public int getItemCountInCategory(String category)

    void incrementClassificationCount(String category)

    void incrementFeatureCount(String feature, String category)

    int getFeatureCountInCategory(String feature, String category)

}