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
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Before
    fun setupIdlingResource() {
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

        launchFragmentInHiltContainer<MoviesFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            adapter.movies = moviesFake
            viewModel = moviesViewModel
        }

        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        /*onView(withId(R.id.rv_movie))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))*/
        onView(withId(R.id.rv_movie))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<MoviesAdapter.MoviesViewHolder>(1, click())
            )
        val movies = moviesFake[1]
        val movieDetailsArg = MoviesFragmentDirections.actionNavigationHomeToMovieDetailsFragment(movies)
        verify(navController).navigate(
            movieDetailsArg
        )
    }
}