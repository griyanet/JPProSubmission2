package com.example.jpsubmission2.view.ui.movies

import com.example.jpsubmission2.utils.DataDummy
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MovieDetailViewModelTest {

    private lateinit var viewModel: MovieDetailViewModel
    private val dummyMovie = DataDummy.generateMovies()[0]
    private val movieId = dummyMovie.movieId

    @Before
    fun setUp() {
        viewModel = MovieDetailViewModel()
        viewModel.selectedMovie(movieId)
    }

    @Test
    fun getMovie() {
        viewModel.selectedMovie(movieId)
        val movies = viewModel.getMovies()
        assertNotNull(movies)
        assertEquals(dummyMovie.movieId, movies.movieId)
        assertEquals(dummyMovie.original_title, movies.original_title)
        assertEquals(dummyMovie.release_date, movies.release_date)
        assertEquals(dummyMovie.overview, movies.overview)
        assertEquals(dummyMovie.budget.toString(), movies.budget.toString())
        assertEquals(dummyMovie.revenue.toString(), movies.revenue.toString())
        assertEquals(dummyMovie.image, movies.image)
    }

}