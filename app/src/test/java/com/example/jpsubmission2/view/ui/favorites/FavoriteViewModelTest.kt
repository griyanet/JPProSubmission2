package com.example.jpsubmission2.view.ui.favorites

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.jpsubmission2.data.local.entity.FavoritesMovie
import com.example.jpsubmission2.data.remote.IMDBAPI
import com.example.jpsubmission2.repository.MockWebServer
import com.example.jpsubmission2.repository.MovieRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest : MockWebServer<IMDBAPI>() {

    private lateinit var api: IMDBAPI
    private lateinit var viewModel: FavoriteViewModel

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MovieRepository

    @Mock
    private lateinit var observer: Observer<List<FavoritesMovie>>

    @Before
    fun setUp() {
        viewModel = FavoriteViewModel(repository)
    }

    @Before
    fun initApi() {
        this.api = createService(IMDBAPI::class.java)
    }

    @Test
    fun getFavorites() {
        val favorites = ArrayList<FavoritesMovie>()
        enqueueResponse("popular_movie.json")
        runBlocking {
            val response = api.getMovies()
            if (response.isSuccessful) {
                val movies = response.body()?.results
                val resultsItem1 = movies?.get(0)
                resultsItem1?.let { FavoritesMovie(0, it) }?.let { favorites.add(it) }
                val resultsItem2 = movies?.get(1)
                resultsItem2?.let { FavoritesMovie(0, it) }?.let { favorites.add(it) }
                val resultsItem3 = movies?.get(2)
                resultsItem3?.let { FavoritesMovie(0, it) }?.let { favorites.add(it) }
                return@runBlocking
            }
        }
        val favoritesLive = MutableLiveData<List<FavoritesMovie>>()
        favoritesLive.value = favorites
        `when`(repository.observeFavorites()).thenReturn(favoritesLive)
        val movieFavorite = viewModel.getFavorites().value
        verify(repository).observeFavorites()
        assertNotNull(movieFavorite)
        assertEquals(3, movieFavorite?.size)

        viewModel.getFavorites().observeForever(observer)
        verify(observer).onChanged(favorites)
    }
}