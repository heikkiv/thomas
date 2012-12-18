package com.heikkiv.ml.thomas

interface Classifier {

    public void train(String document, String category)
    public String classify(String document)

}