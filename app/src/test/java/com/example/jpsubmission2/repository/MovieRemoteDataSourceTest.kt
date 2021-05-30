package com.example.jpsubmission2.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.jpsubmission2.data.remote.IMDBAPI
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieRemoteDataSourceTest : MockWebServer<IMDBAPI>() {

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initApi() {
        this.api = createService(IMDBAPI::class.java)
    }

    private lateinit var api: IMDBAPI

    @Test
    fun getMoviesResponse() {
        enqueueResponse("popular_movie.json")
        runBlocking {
            val response = api.getMovies()
            assertNotNull(response.body()?.results)
            assertThat(response.body()?.results?.get(0)?.movieId,
                Matchers.`is`(460465))
        }
    }

    @Test
    fun getTvsResponse() {
        enqueueResponse("popular_tv.json")
        runBlocking {
            val response = api.getTvs()
            assertNotNull(response.body()?.results)
            assertThat(response.body()?.results?.get(0)?.tvId,
                Matchers.`is`(120168))
        }
    }

    @Test
    fun getMovieDetailsResponse() {
        enqueueResponse("movie_detail_460465.json")
        runBlocking {
            val response = api.getMovieDetails(460465)
            assertNotNull(response.body())
            assertThat(response.body()?.movieId,
                Matchers.`is`(460465))
        }
    }

    @Test
    fun getTvDetailsResponse() {
        enqueueResponse("tv_detail_120168.json")
        runBlocking {
            val response = api.getTvDetails(120168)
            assertNotNull(response.body())
            assertThat(response.body()?.tvId,
                Matchers.`is`(120168))
        }
    }
}