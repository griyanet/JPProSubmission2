package com.example.jpsubmission2.view.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.jpsubmission2.data.remote.responses.MovieDetailResponse
import com.example.jpsubmission2.data.remote.responses.MovieResponse
import com.example.jpsubmission2.data.remote.responses.MovieResultsItem
import com.example.jpsubmission2.getOrAwaitValueTest
import com.example.jpsubmission2.repository.FakeMovieRepository
import com.example.jpsubmission2.util.FakeData
import com.example.jpsubmission2.util.MainCoroutineRule
import com.example.jpsubmission2.utils.Event
import com.example.jpsubmission2.utils.Resource
import com.example.jpsubmission2.utils.Status
import com.google.common.truth.Truth
import junit.framework.Assert
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MovieDetailsViewModelTest {

    @get: Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get: Rule
    val testCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var movieObserver: Observer<Event<Resource<MovieResponse>>>

    private lateinit var viewModel: MovieDetailsViewModel
    private var repository = FakeMovieRepository()

    @Before
    fun setUp() {
        viewModel = MovieDetailsViewModel(repository)
    }

    private var moviId: Int = 0
    private val moviRes = FakeData.generateMoviItems()[0]
    private val favorites = FakeData.generateFavorites()

    @Test
    fun `movieSelected get id from view`() {
        val sSelectedMovi = MutableLiveData<MovieResultsItem>()
        val selectedMovi: LiveData<MovieResultsItem> = sSelectedMovi
        sSelectedMovi.value = moviRes
        moviId = selectedMovi.value?.movieId!!
    }

    @Test
    fun `getMovieDetails Return True`() {

        val mMoviesDetails = MutableLiveData<Event<Resource<MovieDetailResponse>>>()
        val moviesDetails: LiveData<Event<Resource<MovieDetailResponse>>> = mMoviesDetails
        testCoroutineRule.runBlockingTest {

            mMoviesDetails.value = Event(Resource.loading(null))
            val result = repository.getMovieDetails(moviId)
            mMoviesDetails.value = Event(result)
        }
        val testResult = moviesDetails.getOrAwaitValueTest()
        Truth.assertThat(testResult.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
        assertNotNull(testResult)
    }


}