package com.example.jpsubmission2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.jpsubmission2.data.remote.responses.MovieResultsItem
import com.example.jpsubmission2.utils.Constant.FAVORITE_TABLE

@Entity(tableName = FAVORITE_TABLE)
data class FavoritesMovie(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var movieItems: MovieResultsItem,
    var favorite: Boolean = true
)

