package com.example.jpsubmission2.di

import com.example.jpsubmission2.repository.FakeMovieRemoteDataSource
import com.example.jpsubmission2.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class TestRemoteRepositoryModule {

    @Singleton
    @Binds
    abstract fun binRepository(movieRemote: FakeMovieRemoteDataSource): MovieRepository
}