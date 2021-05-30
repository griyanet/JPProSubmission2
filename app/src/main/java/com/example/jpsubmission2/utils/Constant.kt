package com.example.jpsubmission2.utils

object Constant {

    const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/"

    private const val IMAGE_SIZE_W500 = "w500"
    private const val IMAGE_SIZE_W185 = "w185"
    private const val IMAGE_SIZE_W154 = "w154"

    const val BASE_IMAGE_LARGE = BASE_IMAGE_URL + IMAGE_SIZE_W500
    const val BASE_IMAGE_MEDIUM = BASE_IMAGE_URL + IMAGE_SIZE_W185
    const val BASE_IMAGE_SMALL = BASE_IMAGE_URL + IMAGE_SIZE_W154

    const val YOUTUBE_THUMBNAIL_START_URL: String = "https://img.youtube.com/vi/"
    const val YOUTUBE_THUMBNAIL_END_URL: String = "/0.jpg"
    const val YOUTUBE_TRAILER_BASE_URL = "https://www.youtube.com/watch?v="

    //ROOM Database
    const val DATABASE_NAME = "imdb_db"
    const val FAVORITE_TABLE = "favorite_movies_table"

    //Repository Extension
    const val UNABLE_TO_RESOLVE_HOST = "Unable to resolve host"
    const val DATA_ACCESS_ERROR = "Unable to resolve host"

    //Retrofit
    const val NETWORK_TIMEOUT = 10000L
    const val CACHE_TIMEOUT = 2000L
    const val CACHE_ERROR_TIMEOUT = "Cache timeout"
}