package com.example.jpsubmission2.repository

import androidx.lifecycle.LiveData
import com.example.jpsubmission2.data.local.entity.FavoritesMovie
import com.example.jpsubmission2.data.remote.responses.MovieDetailResponse
import com.example.jpsubmission2.data.remote.responses.MovieResponse
import com.example.jpsubmission2.data.remote.responses.TvDetailResponse
import com.example.jpsubmission2.data.remote.responses.TvResponse
import com.example.jpsubmission2.utils.Resource

class DefaultMovieRepository(
    private val remote: MovieDataSource,
    private val local: MovieDataSource
) : MovieRepository {

    override suspend fun getMovies(): Resource<MovieResponse> {
        return remote.getMovies()
    }

    override suspend fun getTvs(): Resource<TvResponse> {
        return remote.getTvs()
    }

    override suspend fun getMovieDetails(id: Int): Resource<MovieDetailResponse> {
        return remote.getMovieDetails(id)
    }

    override suspend fun getTvDetails(id: Int): Resource<TvDetailResponse> {
        return remote.getTvDetails(id)
    }

    override suspend fun insertFavorite(favorite: FavoritesMovie) {
        return local.insertFavorite(favorite)
    }

    override suspend fun deleteFavorite(favorite: FavoritesMovie) {
        return local.deleteFavorite(favorite)
    }

    override fun observeFavorites(): LiveData<List<FavoritesMovie>> {
        return local.observeFavorites()
    }

    override fun getFavoriteItem(id: Int): LiveData<FavoritesMovie> { return local.getFavoriteItem(id) }
}