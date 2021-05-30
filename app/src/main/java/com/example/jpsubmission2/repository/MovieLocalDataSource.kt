package com.example.jpsubmission2.repository

import androidx.lifecycle.LiveData
import com.example.jpsubmission2.data.local.FavoritesDao
import com.example.jpsubmission2.data.local.entity.FavoritesMovie
import com.example.jpsubmission2.data.remote.responses.*
import com.example.jpsubmission2.utils.Resource
import kotlinx.coroutines.coroutineScope

class MovieLocalDataSource internal constructor(private val dao: FavoritesDao) : MovieDataSource {

    override suspend fun getMovies(): Resource<MovieResponse> =
        Resource.success(null)

    override suspend fun getTvs(): Resource<TvResponse> =
        Resource.success(null)

    override suspend fun getMovieDetails(id: Int): Resource<MovieDetailResponse> =
        Resource.success(null)

    override suspend fun getTvDetails(id: Int): Resource<TvDetailResponse> =
        Resource.success(null)

    override suspend fun insertFavorite(favorite: FavoritesMovie) {
        dao.insertFavorite(favorite)
    }

    override suspend fun deleteFavorite(favorite: FavoritesMovie) {
        dao.deleteFavorite(favorite)
    }

    override fun observeFavorites(): LiveData<List<FavoritesMovie>> {
        return dao.observeAllFavorites()
    }

    override fun getFavoriteItem(id: Int): LiveData<FavoritesMovie> {
        return dao.getFavoriteItem(id)
    }

    /*suspend fun isFavoritesSet(id: Int): Boolean {
        val movieDetailItem = getMovieDetails(id)

    }*/
}