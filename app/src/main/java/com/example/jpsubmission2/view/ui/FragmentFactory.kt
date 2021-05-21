package com.example.jpsubmission2.view.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.example.jpsubmission2.adapter.MoviesAdapter
import com.example.jpsubmission2.adapter.TvShowsAdapter
import com.example.jpsubmission2.view.ui.movies.MovieDetailsFragment
import com.example.jpsubmission2.view.ui.movies.MoviesFragment
import com.example.jpsubmission2.view.ui.tvshows.TvShowsDetailsFragment
import com.example.jpsubmission2.view.ui.tvshows.TvShowsFragment
import javax.inject.Inject

class FragmentFactory @Inject constructor(
    private val moviesAdapter: MoviesAdapter,
    private val tvShowsAdapter: TvShowsAdapter,
    private val glide: RequestManager
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            MoviesFragment::class.java.name -> MoviesFragment(moviesAdapter)
            TvShowsFragment::class.java.name -> TvShowsFragment(tvShowsAdapter)
            MovieDetailsFragment::class.java.name -> MovieDetailsFragment(glide)
            TvShowsDetailsFragment::class.java.name -> TvShowsDetailsFragment(glide)
            else -> super.instantiate(classLoader,className)
        }
    }
}