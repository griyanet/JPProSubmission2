package com.example.jpsubmission2.repository

import androidx.lifecycle.LiveData
import com.example.jpsubmission2.data.local.entity.FavoritesMovie
import com.example.jpsubmission2.data.remote.IMDBAPI
import com.example.jpsubmission2.data.remote.responses.MovieDetailResponse
import com.example.jpsubmission2.data.remote.responses.MovieResponse
import com.example.jpsubmission2.data.remote.responses.TvDetailResponse
import com.example.jpsubmission2.data.remote.responses.TvResponse
import com.example.jpsubmission2.utils.Resource
import org.junit.Before

class FakeMovieRemoteDataSource : MovieDataSource, MockWebServer<IMDBAPI>() {

    private lateinit var api: IMDBAPI

    @Before
    fun initApi() {
        this.api = createService(IMDBAPI::class.java)
    }

    override suspend fun getMovies(): Resource<MovieResponse> {
        return try {
            val response = api.getMovies()
            if (response.isSuccessful) {
                response.body().let {
                    return@let Resource.success(it)
                }
            } else {
                Resource.error("Unknown error occurred", null)
            }
        } catch (e: Exception) {
            Resource.error("Couldn't reach the server. Perhaps there is Internet problem!", null)
        }
    }

    override suspend fun getTvs(): Resource<TvResponse> {
        return try {
            val response = api.getTvs()
            if (response.isSuccessful) {
                response.body().let {
                    return@let Resource.success(it)
                }
            } else {
                Resource.error("Unknown error occurred", null)
            }
        } catch (e: Exception) {
            Resource.error("Couldn't reach the server. Perhaps there is Internet problem!", null)
        }
    }

    override suspend fun getMovieDetails(id: Int): Resource<MovieDetailResponse> {
        return try {
            val response = api.getMovieDetails(id)
            if (response.isSuccessful) {
                response.body().let {
                    return@let Resource.success(it)
                }
            } else {
                Resource.error("Unknown error occurred", null)
            }
        } catch (e: Exception) {
            Resource.error("Couldn't reach the server. Perhaps there is Internet problem!", null)
        }
    }

    override suspend fun getTvDetails(id: Int): Resource<TvDetailResponse> {
        return try {
            val response = api.getTvDetails(id)
            if (response.isSuccessful) {
                response.body().let {
                    return@let Resource.success(it)
                }
            } else {
                Resource.error("Unknown error occurred", null)
            }
        } catch (e: Exception) {
            Resource.error("Couldn't reach the server. Perhaps there is Internet problem!", null)
        }
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