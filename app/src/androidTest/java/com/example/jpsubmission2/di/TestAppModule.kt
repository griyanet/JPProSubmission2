package com.example.jpsubmission2.di

import android.content.Context
import androidx.room.Room
import com.example.jpsubmission2.data.local.FavoritesDao
import com.example.jpsubmission2.data.local.IMDBDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named

@Module
@InstallIn(ApplicationComponent::class)
object TestAppModule {

    @Provides
    @Named("test.db")
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, IMDBDatabase::class.java)
            .allowMainThreadQueries()
            .build()

}