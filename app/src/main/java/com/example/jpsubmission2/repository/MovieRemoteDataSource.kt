package com.example.jpsubmission2.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.jpsubmission2.data.local.entity.FavoritesMovie
import com.example.jpsubmission2.data.remote.IMDBAPI
import com.example.jpsubmission2.data.remote.responses.MovieDetailResponse
import com.example.jpsubmission2.data.remote.responses.MovieResponse
import com.example.jpsubmission2.data.remote.responses.TvDetailResponse
import com.example.jpsubmission2.data.remote.responses.TvResponse
import com.example.jpsubmission2.utils.Constant.DATA_ACCESS_ERROR
import com.example.jpsubmission2.utils.Resource

class MovieRemoteDataSource internal constructor(private val api: IMDBAPI) : MovieDataSource {

    override suspend fun getMovies(): Resource<MovieResponse> =
        try {
            val response = api.getMovies()
            if (response.isSuccessful) {
                response.body().let {
                    return@let Resource.success(it)
                }
            } else {
                Resource.error(DATA_ACCESS_ERROR, null)
            }
        } catch (e: Exception) {
            Log.e("EXCEPTION", "EXCEPTION:", e)
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }

    override suspend fun getTvs(): Resource<TvResponse> =
        try {
            val response = api.getTvs()
            if (response.isSuccessful) {
                response.body().let {
                    return@let Resource.success(it)
                }
            } else {
                Resource.error(DATA_ACCESS_ERROR, null)
            }
        } catch (e: Exception) {
            Log.e("EXCEPTION", "EXCEPTION:", e)
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }

    override suspend fun getMovieDetails(id: Int): Resource<MovieDetailResponse> =
        try {
            val response = api.getMovieDetails(id)
            if (response.isSuccessful) {
                response.body().let {
                    return@let Resource.success(it)
                }
            } else {
                Resource.error(DATA_ACCESS_ERROR, null)
            }
        } catch (e: Exception) {
            Log.e("EXCEPTION", "EXCEPTION:", e)
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }

    override suspend fun getTvDetails(id: Int): Resource<TvDetailResponse> =
        try {
            val response = api.getTvDetails(id)
            if (response.isSuccessful) {
                response.body().let {
                    return@let Resource.success(it)
                }
            } else {
                Resource.error(DATA_ACCESS_ERROR, null)
            }
        } catch (e: Exception) {
            Log.e("EXCEPTION", "EXCEPTION:", e)
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }

    override suspend fun insertFavorite(favorite: FavoritesMovie) {
        // not required for remote
    }

    override suspend fun deleteFavorite(favorite: FavoritesMovie) {
        // not required for remote
    }

    override fun observeFavorites(): LiveData<List<FavoritesMovie>> {
        return observeFavorites()
    }


}