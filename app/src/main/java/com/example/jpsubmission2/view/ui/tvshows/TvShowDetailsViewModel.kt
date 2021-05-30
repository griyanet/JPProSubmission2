package com.example.jpsubmission2.view.ui.tvshows

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jpsubmission2.data.remote.responses.TvDetailResponse
import com.example.jpsubmission2.data.remote.responses.TvResultsItem
import com.example.jpsubmission2.repository.MovieRepository
import com.example.jpsubmission2.utils.Event
import com.example.jpsubmission2.utils.Resource
import kotlinx.coroutines.launch

class TvShowDetailsViewModel @ViewModelInject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _tvDetails = MutableLiveData<Event<Resource<TvDetailResponse>>>()
    val tvDetails: LiveData<Event<Resource<TvDetailResponse>>>
    get() = _tvDetails

    private val _selectedTv: MutableLiveData<TvResultsItem> = MutableLiveData()
    private val selectedTv: LiveData<TvResultsItem> = _selectedTv

    private var tvId: Int = 0

    fun tvSelected(tv: TvResultsItem) {
        _selectedTv.value = tv
        tvId = selectedTv.value?.tvId!!
    }

    fun getTvDetails() {
        _tvDetails.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response = repository.getTvDetails(tvId)
            _tvDetails.value = Event(response)
        }
    }
}