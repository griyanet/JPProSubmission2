package com.example.jpsubmission2.view.ui.movies

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.jpsubmission2.R
import com.example.jpsubmission2.utils.DataDummy
import org.junit.Before
import org.junit.Test

class MovieDetailsFragmentTest {

    private val dummyMovies = DataDummy.generateMovies()
    private lateinit var viewModel: MovieDetailViewModel

    @Before
    fun setUp() {
        viewModel = MovieDetailViewModel()
    }

    @Test
    fun testMovieDetailScreen() {
        val dummyMovieId = dummyMovies[5].movieId
        viewModel.selectedMovie(dummyMovieId)
        val movie = viewModel.getMovies()
        val movieScenario = launchFragmentInContainer<MovieDetailsFragment>(bundleOf("movies" to movie))
        movieScenario.moveToState(Lifecycle.State.STARTED)
        onView(withId(R.id.movie_detail))
            .check(matches(isDisplayed()))
        onView(withId(R.id.img_backdrop))
            .check(matches(isDisplayed()))
        onView(withId(R.id.img_poster))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_movie_title))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_movie_title))
            .check(matches(withText("${movie.original_title}")))
        onView(withId(R.id.tv_release_year))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_release_year))
            .check(matches(withText("${movie.release_date}")))
        onView(withId(R.id.tv_budget))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_budget))
            .check(matches(withText("${movie.budget}")))
        onView(withId(R.id.tv_revenue))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_revenue))
            .check(matches(withText("${movie.revenue}")))
        onView(withId(R.id.tv_synopsis))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_synopsis))
            .check(matches(withText("${movie.overview}")))
        onView(withId(R.id.fab_favorite))
            .check(matches(isDisplayed()))
//        onView(withId(R.id.fab_favorite)).perform(click())
//        onView(withId(R.id.fab_favorite)).perform(ViewActions.closeSoftKeyboard())
    }
}