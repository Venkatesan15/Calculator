package com.example.calculator.dataLayer

class Results {

    companion object {
        private val results = mutableListOf<String>()
    }

    fun getResults() : MutableList<String> {
        return results
    }

    fun addToResults(result: String) {
        results.add(result)
    }
    fun clearResults() {
        results.clear()
    }
}