package com.heikkiv.ml.thomas.mongo

import com.heikkiv.ml.thomas.BayesClassifierRepository

import com.gmongo.GMongo

class MongoBayesClassifierRepository implements BayesClassifierRepository {

    def db

    MongoBayesClassifierRepository() {
        def mongo = new GMongo()
        db = mongo.getDB("thomas")
    }

    @Override
    Set<String> getCategories() {
        db.categories.distinct('category')
    }

    @Override
    int getItemCountInCategory(String category) {
        def cat = db.categories.findOne(category: category)
        return (cat) ? cat.documentCount : 0
    }

    @Override
    void incrementClassificationCount(String category) {
        db.categories.update([category: category], [$inc: [documentCount: 1]], true)
    }

    @Override
    void incrementFeatureCount(String feature, String category) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    int getFeatureCountInCategory(String feature, String category) {
        return 0  //To change body of implemented methods use File | Settings | File Templates.
    }
}
