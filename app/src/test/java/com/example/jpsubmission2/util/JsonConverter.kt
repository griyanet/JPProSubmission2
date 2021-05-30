package com.example.jpsubmission2.util

import com.example.jpsubmission2.data.remote.responses.MovieResultsItem
import com.google.gson.Gson

class JsonConverter {

    var gson = Gson()

    fun resultToString(result: MovieResultsItem): String {
        return gson.toJson(result)
    }
}