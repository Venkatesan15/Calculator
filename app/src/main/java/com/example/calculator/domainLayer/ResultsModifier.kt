package com.example.calculator.domainLayer

import com.example.calculator.dataLayer.Results

class ResultsModifier {

    private val resultsObj = Results()

    fun getResults() : MutableList<String> {
        return resultsObj.getResults()
    }

    fun addToResults(result: String) {
        resultsObj.addToResults(result)
    }
    fun clearResults() {
        resultsObj.clearResults()
    }
}