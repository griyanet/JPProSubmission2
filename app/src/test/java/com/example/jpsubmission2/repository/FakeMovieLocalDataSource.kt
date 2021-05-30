package com.example.jpsubmission2.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jpsubmission2.data.local.entity.FavoritesMovie
import com.example.jpsubmission2.data.remote.responses.*
import com.example.jpsubmission2.utils.Resource

class FakeMovieLocalDataSource() : MovieDataSource {

    private val favorites = mutableListOf<FavoritesMovie>()
    private val observableFavoriteMovies = MutableLiveData<List<FavoritesMovie>>(favorites)

    private fun refreshLiveData() {
        observableFavoriteMovies.postValue(favorites)
    }

    override suspend fun getMovies(): Resource<MovieResponse> {
        return Resource.success(data = null)
    }

    override suspend fun getTvs(): Resource<TvResponse> {
        return Resource.success(data = null)
    }

    override suspend fun getMovieDetails(id: Int): Resource<MovieDetailResponse> {
        return Resource.success(data = null)
    }

    override suspend fun getTvDetails(id: Int): Resource<TvDetailResponse> {
        return Resource.success(data = null)
    }

    override suspend fun insertFavorite(favorite: FavoritesMovie) {
        favorites.add(favorite)
        refreshLiveData()
    }

    override suspend fun deleteFavorite(favorite: FavoritesMovie) {
        favorites.remove(favorite)
        refreshLiveData()
    }

    override fun observeFavorites(): LiveData<List<FavoritesMovie>> {
        refreshLiveData()
        return observableFavoriteMovies
    }


}