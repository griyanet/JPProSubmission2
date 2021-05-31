package com.example.jpsubmission2.view.ui.favorites

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jpsubmission2.data.local.entity.FavoritesMovie
import com.example.jpsubmission2.repository.MovieRepository
import kotlinx.coroutines.launch

class FavoriteViewModel @ViewModelInject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _favorite = MutableLiveData<FavoritesMovie>()
    val favorite: LiveData<FavoritesMovie> = _favorite

    fun getFavorites(): LiveData<List<FavoritesMovie>> = repository.observeFavorites()

    fun deleteFavorite(favorite: FavoritesMovie) =
        viewModelScope.launch { repository.deleteFavorite(favorite) }

    fun insertFavorite(favorite: FavoritesMovie) = viewModelScope.launch { repository.insertFavorite(favorite) }



}