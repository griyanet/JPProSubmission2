package com.example.jpsubmission2.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jpsubmission2.data.local.entity.FavoritesMovie
import com.example.jpsubmission2.data.remote.IMDBAPI
import com.example.jpsubmission2.getOrAwaitValueTest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@RunWith(JUnit4::class)
class MovieLocalDataSourceTest : MockWebServer<IMDBAPI>() {

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var api: IMDBAPI
    private val favorites = mutableListOf<FavoritesMovie>()
    private val repository = FakeMovieRepository()

    @Before
    fun initApi() {
        this.api = createService(IMDBAPI::class.java)
    }

    @Test
    fun insertFavorite() {
        enqueueResponse("popular_movie.json")
        runBlockingTest {
            val response = api.getMovies()
            if (response.isSuccessful) {
                val movies = response.body()?.results
                val resultsItem1 = movies?.get(0)
                resultsItem1?.let { FavoritesMovie(0, it) }?.let { repository.insertFavorite(it) }
                val resultsItem2 = movies?.get(1)
                resultsItem2?.let { FavoritesMovie(0, it) }?.let { repository.insertFavorite(it) }
                assertEquals(favorites.size.toLong(), 2)
            }
        }
    }

    @Test
    fun deleteFavorite() {
        runBlockingTest {
            if (favorites.size > 1) {
                val favorite = favorites[0]
                repository.deleteFavorite(favorite)
                assertEquals(favorites.size.toLong(), 1)
            }
        }
    }

    @Test
    fun observeFavorites() {
        val observableFavorites = MutableLiveData<List<FavoritesMovie>>()
        val favoritesLiveData = repository.observeFavorites().getOrAwaitValueTest()
        assertNotNull(favoritesLiveData)
        assertEquals(favoritesLiveData.size.toLong(), 0)
    }
}