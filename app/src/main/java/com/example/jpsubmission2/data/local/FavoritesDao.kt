package com.example.jpsubmission2.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.jpsubmission2.data.local.entity.FavoritesMovie

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoritesMovie)

    @Delete
    suspend fun deleteFavorite(favorite: FavoritesMovie)

    @Query("SELECT * FROM favorite_movies_table")
    fun observeAllFavorites(): LiveData<List<FavoritesMovie>>

    @Query("SELECT * FROM favorite_movies_table WHERE id = :id")
    fun getFavoriteItem(id: Int): LiveData<FavoritesMovie>

}