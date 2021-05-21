package com.example.jpsubmission2.view.ui.favorites

import com.example.jpsubmission2.utils.DataDummy
import com.example.jpsubmission2.viewmodel.FavoriteDetailViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FavoriteDetailViewModelTest {

    lateinit var viewModel: FavoriteDetailViewModel
    private val dummyFavorite = DataDummy.generateFavorites()[4]
    private val favoriteId = dummyFavorite.favMovieId

    @Before
    fun setUp() {
        viewModel = FavoriteDetailViewModel()
        viewModel.selectedFavMovie(favoriteId)
    }

    @Test
    fun getFavorite() {
        viewModel.selectedFavMovie(favoriteId)
        val favorites = viewModel.getFavoriteMovies()
        assertNotNull(favorites)
        assertEquals(dummyFavorite.favMovieId, favorites.favMovieId)
        assertEquals(dummyFavorite.original_title, favorites.original_title)
        assertEquals(dummyFavorite.release_date, favorites.release_date)
        assertEquals(dummyFavorite.overview, favorites.overview)
        assertEquals(dummyFavorite.budget.toString(), favorites.budget.toString())
        assertEquals(dummyFavorite.revenue.toString(), favorites.revenue.toString())
        assertEquals(dummyFavorite.image, favorites.image)
    }
}