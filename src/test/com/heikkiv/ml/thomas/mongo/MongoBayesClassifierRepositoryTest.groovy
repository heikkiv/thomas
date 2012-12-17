package com.heikkiv.ml.thomas.mongo

import org.junit.Test
import static org.junit.Assert.assertEquals

public class MongoBayesClassifierRepositoryTest {

    @Test
    public void testGetCategories() throws Exception {
        MongoBayesClassifierRepository repo = new MongoBayesClassifierRepository()
        assertEquals(['test_category', 'some_category'] as Set, repo.getCategories())
    }

    @Test
    public void testGetItemCountInCategory() throws Exception {
        MongoBayesClassifierRepository repo = new MongoBayesClassifierRepository()
        int n = repo.getItemCountInCategory('some_category')
        assertEquals(3, n)
    }

    @Test
    public void testIncrementClassificationCount() throws Exception {
        MongoBayesClassifierRepository repo = new MongoBayesClassifierRepository()
        int n = repo.getItemCountInCategory('test_category')
        repo.incrementClassificationCount('test_category')
        int m = repo.getItemCountInCategory('test_category')
        assertEquals(n+1, m)
    }

    @Test
    public void testIncrementFeatureCount() throws Exception {
        MongoBayesClassifierRepository repo = new MongoBayesClassifierRepository()
        int n = repo.getFeatureCountInCategory('koira', 'bad')
        repo.incrementFeatureCount('koira', 'bad')
        int m = repo.getFeatureCountInCategory('koira', 'bad')
        assertEquals(n+1, m)
    }

    @Test
    public void testGetFeatureCountInCategory() throws Exception {
        MongoBayesClassifierRepository repo = new MongoBayesClassifierRepository()
        int n = repo.getFeatureCountInCategory('kissa', 'good')
        assertEquals(2, n)
    }
}
