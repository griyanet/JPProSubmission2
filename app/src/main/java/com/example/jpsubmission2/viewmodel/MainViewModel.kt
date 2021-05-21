package com.example.jpsubmission2.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jpsubmission2.data.remote.responses.*
import com.example.jpsubmission2.repository.Repository
import com.example.jpsubmission2.utils.Event
import com.example.jpsubmission2.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _movies: MutableLiveData<Event<Resource<MovieResponse>>> = MutableLiveData()
    val movies: LiveData<Event<Resource<MovieResponse>>> = _movies

    private val _tvs: MutableLiveData<Event<Resource<TvResponse>>> = MutableLiveData()
    val tvs: LiveData<Event<Resource<TvResponse>>> = _tvs

    private val _movieDetails: MutableLiveData<Event<Resource<MovieDetailResponse>>> =
        MutableLiveData()
    val movieDetails: LiveData<Event<Resource<MovieDetailResponse>>> = _movieDetails

    private val _tvDetails: MutableLiveData<Event<Resource<TvDetailResponse>>> = MutableLiveData()
    val tvDetails: LiveData<Event<Resource<TvDetailResponse>>> = _tvDetails

    private val _selectedMovie: MutableLiveData<MovieResultsItem> = MutableLiveData()
    private val selectedMovie: LiveData<MovieResultsItem> = _selectedMovie

    private val _selectedTv: MutableLiveData<TvResultsItem> = MutableLiveData()
    private val selectedTv: LiveData<TvResultsItem> = _selectedTv

    private var movieId: Int = 0
    private var tvId: Int = 0

    fun movieSelected(movie: MovieResultsItem) {
        _selectedMovie.value = movie
        movieId = selectedMovie.value?.movieId!!
    }

    fun tvSelected(tv: TvResultsItem) {
        _selectedTv.value = tv
        tvId = selectedTv.value?.tvId!!
    }

    fun getMovies() = viewModelScope.launch {
        _movies.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response = repository.remote.getMovies()
            _movies.value = Event(response)
        }
    }

    fun getTvs() = viewModelScope.launch {
        _tvs.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response = repository.remote.getTvs()
            _tvs.value = Event(response)
        }
    }

    fun getMovieDetails() = viewModelScope.launch {
        _movieDetails.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response = repository.remote.getMovieDetails(movieId)
            _movieDetails.value = Event(response)
        }
    }

    fun getTvDetails() = viewModelScope.launch {
        _tvDetails.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response = repository.remote.getTvDetails(tvId)
            _tvDetails.value = Event(response)
        }
    }
}