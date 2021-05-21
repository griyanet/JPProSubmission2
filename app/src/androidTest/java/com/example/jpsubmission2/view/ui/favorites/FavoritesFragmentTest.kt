package com.example.jpsubmission2.view.ui.favorites

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
import com.example.jpsubmission2.viewmodel.FavoritesViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify


@MediumTest
@RunWith(AndroidJUnit4::class)
class FavoritesFragmentTest {

    private lateinit var viewModel: FavoritesViewModel

    @Before
    fun setUp() {
        viewModel = FavoritesViewModel()
    }

    @Test
    fun testNavigationToFavoriteDetailScreen() {
        val favorite04 = viewModel.getFavorites()[4]
        val favoriteScenario = launchFragmentInContainer<FavoritesFragment>(
            Bundle(),
            R.style.Theme_JPSubmission1
        )
        val navController = mock(NavController::class.java)
        favoriteScenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        onView(withId(R.id.rv_Favorite)).check(matches(isDisplayed()))
        Thread.sleep(3000)
        onView(withId(R.id.rv_Favorite))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(4))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    4,
                    click()
                )
            )
        Thread.sleep(3000)
        verify(navController).navigate(
            FavoritesFragmentDirections.actionFavoritesToFavoriteDetailFragment(favorite04)
        )
    }
}