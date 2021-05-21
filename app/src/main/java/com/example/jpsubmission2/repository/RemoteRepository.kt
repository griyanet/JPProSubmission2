package com.example.jpsubmission2.repository

import com.example.jpsubmission2.data.remote.responses.MovieDetailResponse
import com.example.jpsubmission2.data.remote.responses.MovieResponse
import com.example.jpsubmission2.data.remote.responses.TvDetailResponse
import com.example.jpsubmission2.data.remote.responses.TvResponse
import com.example.jpsubmission2.utils.Resource

interface RemoteRepository {

    suspend fun getMovies(): Resource<MovieResponse>
    suspend fun getTvs(): Resource<TvResponse>
    suspend fun getMovieDetails(id: Int): Resource<MovieDetailResponse>
    suspend fun getTvDetails(id: Int): Resource<TvDetailResponse>
}