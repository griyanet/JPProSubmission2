package com.example.jpsubmission2.view.ui.movies

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.jpsubmission2.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify


@MediumTest
@RunWith(AndroidJUnit4::class)
class MoviesFragmentTest {

    private lateinit var viewModel: MoviesViewModel

    @Before
    fun setUp() {
        viewModel = MoviesViewModel()
    }

    @Test
    fun testNavigationToMovieDetailScreen() {
        val movie05 = viewModel.getMovies()[5]
        val movieScenario = launchFragmentInContainer<MoviesFragment>(
            Bundle(),
            R.style.Theme_JPSubmission1
        )
        val navController = mock(NavController::class.java)
        movieScenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        Thread.sleep(3000)
        onView(withId(R.id.rv_movie))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    5,
                    click()
                )
            )
        Thread.sleep(3000)
        verify(navController).navigate(
            MoviesFragmentDirections.actionNavigationHomeToMovieDetailsFragment(movie05)
        )
    }
}