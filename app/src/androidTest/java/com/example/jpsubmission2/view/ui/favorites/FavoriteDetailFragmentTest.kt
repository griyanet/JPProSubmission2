package com.example.jpsubmission2.view.ui.favorites

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.jpsubmission2.R
import com.example.jpsubmission2.utils.DataDummy
import com.example.jpsubmission2.viewmodel.FavoriteDetailViewModel
import org.junit.Before
import org.junit.Test

class FavoriteDetailsFragmentTest {

    private val dummyFavorites = DataDummy.generateFavorites()
    private lateinit var viewModel: FavoriteDetailViewModel

    @Before
    fun setUp() {
        viewModel = FavoriteDetailViewModel()
    }

    @Test
    fun testFavoriteDetailScreen() {
        val dummyFavoriteId = dummyFavorites[3].favMovieId
        viewModel.selectedFavMovie(dummyFavoriteId)
        val favMovie = viewModel.getFavoriteMovies()
        val favoriteScenario = launchFragmentInContainer<FavoriteDetailFragment>(bundleOf("favmovies" to favMovie))
        favoriteScenario.moveToState(Lifecycle.State.STARTED)
        onView(withId(R.id.favorite_detail))
            .check(matches(isDisplayed()))
        onView(withId(R.id.fav_img_backdrop))
            .check(matches(isDisplayed()))
        onView(withId(R.id.fav_img_poster))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_fav_title))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_fav_title))
            .check(matches(withText("${favMovie.original_title}")))
        onView(withId(R.id.tv_fav_release_year))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_fav_release_year))
            .check(matches(withText("${favMovie.release_date}")))
        onView(withId(R.id.tv_fav_budget))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_fav_budget))
            .check(matches(withText("${favMovie.budget}")))
        onView(withId(R.id.tv_fav_revenue))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_fav_revenue))
            .check(matches(withText("${favMovie.revenue}")))
        onView(withId(R.id.tv_fav_synopsis))
            .check(matches(isDisplayed()))
        onView(withId(R.id.tv_fav_synopsis))
    }
}