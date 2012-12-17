package com.heikkiv.ml.thomas.mongo

class HashMapCategoryRepository implements CategoryRepository {

    def categoryCounts = [:]

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
}
