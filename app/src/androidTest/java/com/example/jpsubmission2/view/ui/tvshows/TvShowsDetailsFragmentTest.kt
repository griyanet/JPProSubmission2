package com.example.jpsubmission2.view.ui.tvshows

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

class TvShowsDetailsFragmentTest {

    private val dummyTvShows = DataDummy.generateTVShows()
    private lateinit var viewModel: TvShowDetailViewModel

    @Before
    fun setUp() {
        viewModel = TvShowDetailViewModel()
    }

    @Test
    fun testTvShowDetailScreen() {
        val dummyTvShowArgId = dummyTvShows[5].tvshowsId
        viewModel.selectedTvShow(dummyTvShowArgId)
        val tvShows = viewModel.getTvShow()
        val tvShowScenario = launchFragmentInContainer<TvShowsDetailsFragment>(bundleOf("tvShows" to tvShows))
        tvShowScenario.moveToState(Lifecycle.State.STARTED)
        onView(withId(R.id.tvShow_detail_fragment)).check(matches(isDisplayed()))
        onView(withId(R.id.img_tv_shows_backdrop)).check(matches(isDisplayed()))
        onView(withId(R.id.img_tv_shows_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tvShows_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tvShows_title)).check(matches(withText("${tvShows.title}")))
        onView(withId(R.id.tv_tvShows_release_year)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tvShows_release_year)).check(matches(withText("${tvShows.release_year}")))
        onView(withId(R.id.tv_tvShow_synopsis)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_tvShow_synopsis)).check(matches(withText("${tvShows.overview}")))
    }
}