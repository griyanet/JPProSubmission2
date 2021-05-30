package com.example.jpsubmission2.view.ui.movies

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.example.jpsubmission2.R
import com.example.jpsubmission2.data.local.entity.FavoritesMovie
import com.example.jpsubmission2.data.remote.responses.MovieDetailResponse
import com.example.jpsubmission2.databinding.FragmentMovieContentBinding
import com.example.jpsubmission2.databinding.FragmentMovieDetailsBinding
import com.example.jpsubmission2.utils.Constant
import com.example.jpsubmission2.utils.Status
import com.example.jpsubmission2.utils.snack
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment @Inject constructor(
    private val glide: RequestManager
) : Fragment(R.layout.fragment_movie_details) {

    private lateinit var movieDetailBinding: FragmentMovieDetailsBinding
    private lateinit var movieContentBinding: FragmentMovieContentBinding
    private val args by navArgs<MovieDetailsFragmentArgs>()
    lateinit var viewModel: MovieDetailsViewModel
    private var isFavorite = false
    private var favoriteId = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMovieDetailsBinding.bind(view)
        movieDetailBinding = binding
        movieContentBinding = movieDetailBinding.movieContentFragment
        viewModel = ViewModelProvider(requireActivity()).get(MovieDetailsViewModel::class.java)
        viewModel.movieSelected(args.movies)
        viewModel.getMovieDetails()
        viewModel.movieDetails.observe(viewLifecycleOwner, {
            Log.d("MovieDetailFragment", "Read Movie Detail from Network")
            it.getContentIfNotHandled().let { result ->
                when (result?.status) {
                    Status.SUCCESS -> {
                        result.data?.let { movieDetails ->
                            populateMovie(movieDetails)
                        }
                        movieDetailBinding.pbMovieDetail.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        movieDetailBinding.movieDetail.snack(R.string.movie_detail_fragment_resource_error)
                        movieDetailBinding.pbMovieDetail.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        movieDetailBinding.pbMovieDetail.visibility = View.VISIBLE
                    }
                }
            }
        })
        checkSavedFavorites()
        movieContentBinding.fabFavorite.setOnClickListener {
            if (!isFavorite) {
                saveToFavorite()
            } else {
                deleteFavorite(favoriteId)
            }
        }
    }

    private fun saveToFavorite() {
        if (!isFavorite) {
            val favorite = FavoritesMovie(0, args.movies, true)
            viewModel.insertFavorite(favorite)
            movieDetailBinding.movieDetail.snack(R.string.addFavorite)
            changeButtonStatus(true, R.drawable.ic_add_favorite)
        }
    }

    private fun changeButtonStatus(isFavorite: Boolean, button: Int) {
        this.isFavorite = isFavorite
        movieContentBinding.fabFavorite.setImageResource(button)
    }

    private fun checkSavedFavorites() {
        Log.d("checkFavorite", "checkSavedFavorite called!")
        viewModel.getAllFavorite()
        viewModel.isMovieExistInFavorite()
        viewModel.isFavorite.observe(viewLifecycleOwner, {
            Log.d("checkFavoriteStatus", it.toString())
            if (isFavorite) {
                viewModel.getFavorite()
                favoriteId = viewModel.favoriteId.value!!
                changeButtonStatus(true, R.drawable.ic_add_favorite)
            } else {
                changeButtonStatus(false, R.drawable.ic_favorite_blank)
            }
        })

    }

    private fun deleteFavorite(id: Int) {
        val favorite = FavoritesMovie(id, args.movies)
        viewModel.deleteFavorite(favorite)
        changeButtonStatus(false, R.drawable.ic_favorite_blank)
        movieDetailBinding.movieDetail.snack(R.string.favorite_delete)
    }

    private fun populateMovie(result: MovieDetailResponse) {
        val posterImg = Constant.BASE_IMAGE_SMALL + result.posterPath
        val backdropImg = Constant.BASE_IMAGE_LARGE + result.backdropPath
        with(movieContentBinding) {
            glide.load(posterImg).into(imgPoster)
            glide.load(backdropImg).into(imgBackdrop)
            tvMovieTitle.text = result.originalTitle
            tvReleaseYear.text = result.releaseDate
            tvBudget.text = result.budget.toString()
            tvRevenue.text = result.revenue.toString()
            tvSynopsis.text = result.overview
        }
    }
}