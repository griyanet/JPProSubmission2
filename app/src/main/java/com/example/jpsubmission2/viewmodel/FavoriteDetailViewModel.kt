package com.example.jpsubmission2.viewmodel

import androidx.lifecycle.ViewModel
import com.example.jpsubmission2.data.local.FavoritesMovie
import com.example.jpsubmission2.utils.DataDummy

class FavoriteDetailViewModel: ViewModel() {

    private lateinit var favMovieId: String

    fun selectedFavMovie(favMovieId: String) {
        this.favMovieId = favMovieId
    }

    fun getFavoriteMovies(): FavoritesMovie {
        lateinit var favMovie: FavoritesMovie
        val favMoviesCol = DataDummy.generateFavorites()
        for (FavoritesMovie in favMoviesCol) {
            if (FavoritesMovie.favMovieId == favMovieId) {
                favMovie = FavoritesMovie
            }
        }
        return favMovie
    }
}