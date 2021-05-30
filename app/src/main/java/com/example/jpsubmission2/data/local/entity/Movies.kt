package com.example.jpsubmission2.data.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


data class Movies(
    var movieId: String,
    var original_title: String,
    var release_date: String,
    var overview: String,
    var budget: String,
    var revenue: String,
    var image: Int
    )

