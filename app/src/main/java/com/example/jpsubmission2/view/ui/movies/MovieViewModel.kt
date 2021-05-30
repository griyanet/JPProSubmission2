package com.example.jpsubmission2.view.ui.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jpsubmission2.data.remote.responses.MovieResponse
import com.example.jpsubmission2.repository.MovieRepository
import com.example.jpsubmission2.utils.Event
import com.example.jpsubmission2.utils.Resource
import kotlinx.coroutines.launch

class MovieViewModel @ViewModelInject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movies = MutableLiveData<Event<Resource<MovieResponse>>>()
    val movies: LiveData<Event<Resource<MovieResponse>>>
        get() = _movies

    fun getMovies() {
        _movies.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response = repository.getMovies()
            _movies.value = Event(response)
        }
    }
}