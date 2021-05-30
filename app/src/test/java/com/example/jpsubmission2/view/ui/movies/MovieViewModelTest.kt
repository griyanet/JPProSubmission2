package com.example.jpsubmission2.view.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.jpsubmission2.data.remote.responses.MovieResponse
import com.example.jpsubmission2.getOrAwaitValueTest
import com.example.jpsubmission2.repository.FakeMovieRepository
import com.example.jpsubmission2.util.MainCoroutineRule
import com.example.jpsubmission2.utils.Event
import com.example.jpsubmission2.utils.Resource
import com.example.jpsubmission2.utils.Status
import com.google.common.truth.Truth.assertThat
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MovieViewModelTest {

    @get: Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get: Rule
    val testCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var movieObserver: Observer<Event<Resource<MovieResponse>>>

    private lateinit var viewModel: MovieViewModel
    private var repository = FakeMovieRepository()

    @Before
    fun setUp() {
        viewModel = MovieViewModel(repository)
    }

    @Test
    fun `getMovies Return True`() {

        val mMovies = MutableLiveData<Event<Resource<MovieResponse>>>()
        val movies: LiveData<Event<Resource<MovieResponse>>> = mMovies
        testCoroutineRule.runBlockingTest {
            mMovies.value = Event(Resource.loading(null))
            val result = repository.getMovies()
            mMovies.value = Event(result)
        }
        val testResult = movies.getOrAwaitValueTest()
        assertThat(testResult.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
        assertNotNull(testResult)
    }
}

