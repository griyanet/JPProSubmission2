package com.example.jpsubmission2.view.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.jpsubmission2.R
import com.example.jpsubmission2.adapter.MoviesAdapter
import com.example.jpsubmission2.launchFragmentInHiltContainer
import com.example.jpsubmission2.repository.FakeMovieRepositoryAndroidTest
import com.example.jpsubmission2.utils.EspressoIdlingResource
import com.example.jpsubmission2.utils.FakeDataTest
import com.example.jpsubmission2.view.ui.FragmentFactory
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@HiltAndroidTest
@MediumTest
@ExperimentalCoroutinesApi
class MoviesFragmentTest {


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var favoriteFragmentFactory: FragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun checkRecyclerViewInMovieFragment() {
        val moviesFake = FakeDataTest.generateMoviItems()
        val navController = mock(NavController::class.java)
        val moviesViewModel = MovieViewModel(FakeMovieRepositoryAndroidTest())
        val adapter = MoviesAdapter()

        launchFragmentInHiltContainer<MoviesFragment>(fragmentFactory = favoriteFragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            adapter.movies = moviesFake
            viewModel = moviesViewModel
        }

        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))
        Thread.sleep(2000)
        onView(withId(R.id.rv_movie))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click())
            )
        val moviesFakeArg = moviesFake[1]
        val movieDetailsArg = MoviesFragmentDirections.actionNavigationHomeToMovieDetailsFragment(moviesFakeArg)
        verify(navController).navigate(
            movieDetailsArg
        )
    }
}