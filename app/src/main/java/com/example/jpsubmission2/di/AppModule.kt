package com.example.jpsubmission2.di

import android.content.Context
import androidx.databinding.library.BuildConfig
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.jpsubmission2.R
import com.example.jpsubmission2.data.local.IMDBDatabase
import com.example.jpsubmission2.data.remote.IMDBAPI
import com.example.jpsubmission2.repository.*
import com.example.jpsubmission2.utils.Constant.BASE_URL
import com.example.jpsubmission2.utils.Constant.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.annotation.AnnotationRetention.RUNTIME

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Qualifier
    @Retention(RUNTIME)
    annotation class RemoteMovieDataSource

    @Qualifier
    @Retention(RUNTIME)
    annotation class LocalMovieDataSource

    @Singleton
    @RemoteMovieDataSource
    @Provides
    fun provideMovieRemoteDataSource(api: IMDBAPI): MovieDataSource {
        return MovieRemoteDataSource(api)
    }

    @Singleton
    @LocalMovieDataSource
    @Provides
    fun provideMovieLocalDataSource(db: IMDBDatabase): MovieDataSource {
        return MovieLocalDataSource(db.favoritesDao())
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build()
        } else
            OkHttpClient
                .Builder()
                .build()

    @Singleton
    @Provides
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): IMDBAPI = retrofit.create(IMDBAPI::class.java)

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_loading)
            .error(R.drawable.ic_image_error)
    )

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, IMDBDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDao(
        database: IMDBDatabase
    ) = database.favoritesDao()

}

@Module
@InstallIn(ApplicationComponent::class)
object MovieRepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        @AppModule.RemoteMovieDataSource movieRemoteDataSource: MovieDataSource,
        @AppModule.LocalMovieDataSource movieLocalDataSource: MovieDataSource
    ): MovieRepository {
        return DefaultMovieRepository(movieRemoteDataSource, movieLocalDataSource)
    }
}