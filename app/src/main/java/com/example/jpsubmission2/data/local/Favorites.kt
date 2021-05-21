package com.example.jpsubmission2.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FavoritesMovie(
    var favMovieId: String,
    var original_title: String,
    var release_date: String,
    var overview: String,
    var budget: String,
    var revenue: String,
    var image: Int
) : Parcelable

