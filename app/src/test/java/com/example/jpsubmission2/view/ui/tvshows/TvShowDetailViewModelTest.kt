package com.example.jpsubmission2.view.ui.tvshows

import com.example.jpsubmission2.utils.DataDummy
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class TvShowDetailViewModelTest {

    private lateinit var viewModel: TvShowDetailViewModel
    private val dummyTvShow = DataDummy.generateTVShows()[0]
    private val tvShowId = dummyTvShow.tvshowsId

    @Before
    fun setUp() {
        viewModel = TvShowDetailViewModel()
        viewModel.selectedTvShow(tvShowId)
    }

    @Test
    fun getTvShow() {
        viewModel.selectedTvShow(tvShowId)
        val tvShows = viewModel.getTvShow()
        assertNotNull(tvShows)
        assertEquals(dummyTvShow.tvshowsId, tvShows.tvshowsId)
        assertEquals(dummyTvShow.title, tvShows.title)
        assertEquals(dummyTvShow.release_year, tvShows.release_year)
        assertEquals(dummyTvShow.overview, tvShows.overview)
        assertEquals(dummyTvShow.image, tvShows.image)
    }
}