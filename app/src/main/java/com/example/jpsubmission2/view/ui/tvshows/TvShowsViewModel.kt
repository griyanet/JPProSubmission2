package com.example.jpsubmission2.view.ui.tvshows

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jpsubmission2.data.remote.responses.TvResponse
import com.example.jpsubmission2.repository.MovieRepository
import com.example.jpsubmission2.utils.Event
import com.example.jpsubmission2.utils.Resource
import kotlinx.coroutines.launch

class TvShowsViewModel @ViewModelInject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _tvs = MutableLiveData<Event<Resource<TvResponse>>>()
    val tvs: LiveData<Event<Resource<TvResponse>>>
    get() = _tvs

    fun getTvs() {
        _tvs.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response = repository.getTvs()
            _tvs.value = Event(response)
        }
    }
}