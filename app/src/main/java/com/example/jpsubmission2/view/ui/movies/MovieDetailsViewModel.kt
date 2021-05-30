package com.example.jpsubmission2.view.ui.movies

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jpsubmission2.data.local.entity.FavoritesMovie
import com.example.jpsubmission2.data.remote.responses.MovieDetailResponse
import com.example.jpsubmission2.data.remote.responses.MovieResultsItem
import com.example.jpsubmission2.repository.Event
import com.example.jpsubmission2.repository.MovieRepository
import com.example.jpsubmission2.utils.Resource
import com.example.jpsubmission2.view.ui.favorites.FavoriteViewModel
import kotlinx.coroutines.launch

class MovieDetailsViewModel @ViewModelInject constructor(private val repository: MovieRepository) :
    ViewModel() {

    private val favViewModel = FavoriteViewModel(repository)
    private val _movieDetails = MutableLiveData<Event<Resource<MovieDetailResponse>>>()
    val movieDetails: LiveData<Event<Resource<MovieDetailResponse>>>
    get() = _movieDetails

    private val selectedMovie = MutableLiveData<MovieResultsItem>()

    private val _favoritesList = MutableLiveData<List<FavoritesMovie>>()
    val favoriteList: LiveData<List<FavoritesMovie>> = _favoritesList

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private val _favoriteId = MutableLiveData<Int>()
    val favoriteId: LiveData<Int> = _favoriteId

    private var movieId: Int = 0


    fun movieSelected(movie: MovieResultsItem) {
        selectedMovie.value = movie
        movieId = selectedMovie.value?.movieId!!
    }

    fun getMovieDetails() {
        _movieDetails.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response = repository.getMovieDetails(movieId)
            _movieDetails.value = Event(response)
        }
    }

    fun insertFavorite(favoritesMovie: FavoritesMovie) {
        favViewModel.insertFavorite(favoritesMovie)
    }

    fun deleteFavorite(favoritesMovie: FavoritesMovie) {
        favViewModel.deleteFavorite(favoritesMovie)
    }

    fun getAllFavorite(): LiveData<List<FavoritesMovie>> {
        return favViewModel.getFavorites()
    }

    fun setAllFavorite(list: List<FavoritesMovie>) {
        _favoritesList.value = list
    }

    fun setIsFavorite(isFav: Boolean) {
        _isFavorite.value = isFav
    }

    fun setFavoriteId(id: Int) {
        _favoriteId.value = id
    }
}