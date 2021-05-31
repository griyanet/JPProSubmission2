package com.example.jpsubmission2.view.ui.favorites

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.example.jpsubmission2.R
import com.example.jpsubmission2.launchFragmentInHiltContainer
import com.example.jpsubmission2.repository.FakeMovieRepositoryAndroidTest
import com.example.jpsubmission2.util.MainCoroutineRule
import com.example.jpsubmission2.utils.FakeDataTest
import com.example.jpsubmission2.view.ui.FragmentFactory
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify
import javax.inject.Inject


@HiltAndroidTest
@MediumTest
@ExperimentalCoroutinesApi
class FavoritesFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get: Rule
    val testCoroutineRule = MainCoroutineRule()

    @Inject
    lateinit var favFragmentFactory: FragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    private var repository = FakeMovieRepositoryAndroidTest()

    @Test
    fun checkRecyclerViewInFavoriteFragment() {
        val favoriteFake = FakeDataTest.generateFavorites()
        testCoroutineRule.runBlockingTest {
            repository.insertFavorite(favoriteFake[0])
            repository.insertFavorite(favoriteFake[1])
        }

        val navController = Mockito.mock(NavController::class.java)
        val favViewModel = FavoriteViewModel(FakeMovieRepositoryAndroidTest())

        launchFragmentInHiltContainer<FavoritesFragment>(fragmentFactory = favFragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = favViewModel
        }

        Espresso.onView(ViewMatchers.withId(R.id.rv_Favorite))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.rv_Favorite))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))
        Espresso.onView(ViewMatchers.withId(R.id.rv_Favorite))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    ViewActions.click()
                )
            )
        val favMoviesItem = favoriteFake[0].movieItems
        val favoriteId = favoriteFake[0].id
        val favDetailsArg = FavoritesFragmentDirections.actionFavoritesToMovieDetailsFragment(
            favMoviesItem,
            favoriteId
        )
        Mockito.verify(navController).navigate(
            favDetailsArg
        )
    }
}