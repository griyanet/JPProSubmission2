package com.example.jpsubmission2.utils

import androidx.room.TypeConverter
import com.example.jpsubmission2.data.remote.responses.MovieResultsItem
import com.example.jpsubmission2.data.remote.responses.TvResultsItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataTypeConverter {

    private var gson = Gson()

    @TypeConverter
    fun movieItemToString(movieItem: MovieResultsItem): String {
        return gson.toJson(movieItem)
    }

    @TypeConverter
    fun stringToMovieItem(data: String): MovieResultsItem {
        val listType = object : TypeToken<MovieResultsItem>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun tvItemToString(result: TvResultsItem): String {
        return gson.toJson(result)
    }

    @TypeConverter
    fun stringToTvItem(data: String): TvResultsItem {
        val listType = object : TypeToken<TvResultsItem>() {}.type
        return gson.fromJson(data, listType)
    }

}