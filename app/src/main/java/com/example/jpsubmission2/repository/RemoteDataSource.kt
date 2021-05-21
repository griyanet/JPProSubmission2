package com.example.jpsubmission2.repository

import android.util.Log
import com.example.jpsubmission2.data.remote.IMDBAPI
import com.example.jpsubmission2.data.remote.responses.MovieDetailResponse
import com.example.jpsubmission2.data.remote.responses.MovieResponse
import com.example.jpsubmission2.data.remote.responses.TvDetailResponse
import com.example.jpsubmission2.data.remote.responses.TvResponse
import com.example.jpsubmission2.utils.Resource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api: IMDBAPI
) : RemoteRepository {

    override suspend fun getMovies(): Resource<MovieResponse> {
        return try {
            val response = api.getMovies()
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Success but failed! Error is unknown.", null)
            } else {
                Resource.error("An unknown error occurred. Api failed!", null)
            }
        } catch (e: Exception) {
            Log.e("Exception", "Exception", e)
            Resource.error("Couldn't reach the server. Perhaps there is Internet problem!", null)
        }
    }

    override suspend fun getTvs(): Resource<TvResponse> {
        return try {
            val response = api.getTvs()
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Success but failed! Error is unknown", null)
            } else {
                Resource.error("An unknown error occurred. Api failed!", null)
            }
        } catch (e: Exception) {
            Log.e("Exception", "Exception", e)
            Resource.error("Couldn't reach the server. Perhaps there is Internet problem!", null)
        }
    }

    override suspend fun getMovieDetails(id: Int): Resource<MovieDetailResponse> {
        return try {
            val response = api.getMovieDetails(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Success but failed! Error is unknown", null)
            } else {
                Resource.error("An unknown error occurred. Api failed!", null)
            }
        } catch (e: Exception) {
            Log.e("Exception", "Exception", e)
            Resource.error("Couldn't reach the server. Perhaps there is Internet problem!", null)
        }
    }

    override suspend fun getTvDetails(id: Int): Resource<TvDetailResponse> {
        return try {
            val response = api.getTvDetails(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Success but failed! Error is unknown", null)
            } else {
                Resource.error("An unknown error occurred. Api failed!", null)
            }
        } catch (e: Exception) {
            Log.e("Exception", "Exception", e)
            Resource.error("Couldn't reach the server. Perhaps there is Internet problem!", null)
        }
    }


}
