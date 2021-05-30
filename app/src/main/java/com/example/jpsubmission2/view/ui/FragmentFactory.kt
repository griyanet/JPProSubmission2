package com.example.jpsubmission2.view.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.example.jpsubmission2.view.ui.movies.MovieDetailsFragment
import com.example.jpsubmission2.view.ui.tvshows.TvShowsDetailsFragment
import javax.inject.Inject

class FragmentFactory @Inject constructor(
    private val glide: RequestManager
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            TvShowsDetailsFragment::class.java.name -> TvShowsDetailsFragment(glide)
            MovieDetailsFragment::class.java.name -> MovieDetailsFragment(glide)
            else -> super.instantiate(classLoader, className)
        }
    }
}