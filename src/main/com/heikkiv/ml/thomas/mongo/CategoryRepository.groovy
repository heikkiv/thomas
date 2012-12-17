package com.heikkiv.ml.thomas.mongo

public interface CategoryRepository {

    Set<String> getCategories()

    public int getItemCountInCategory(String category)

    void incrementClassificationCount(String category)

}