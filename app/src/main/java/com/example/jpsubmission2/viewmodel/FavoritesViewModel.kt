package com.example.jpsubmission2.viewmodel

import androidx.lifecycle.ViewModel
import com.example.jpsubmission2.data.local.FavoritesMovie
import com.example.jpsubmission2.utils.DataDummy

class FavoritesViewModel : ViewModel() {

       fun getFavorites(): List<FavoritesMovie> = DataDummy.generateFavorites()
}

