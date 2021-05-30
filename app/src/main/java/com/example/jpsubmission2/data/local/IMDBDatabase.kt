package com.example.jpsubmission2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.jpsubmission2.data.local.entity.FavoritesMovie
import com.example.jpsubmission2.utils.DataTypeConverter

@Database(
    entities = [FavoritesMovie::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DataTypeConverter::class)
abstract class IMDBDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}