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
                checkSavedFavorites()
                setFavoriteId()
                deleteFavorite(favoriteId)
            }
        }
    }

    private fun saveToFavorite() {
        if (!isFavorite) {
            val favorite = FavoritesMovie(0, args.movies, true)
            viewModel.insertFavorite(favorite)
            movieDetailBinding.movieDetail.snack(R.string.addFavorite)
            changeButtonStatus(true)
            checkSavedFavorites()
        }
    }
    private fun deleteFavorite(id: Int) {
        val favorite = FavoritesMovie(id, args.movies)
        viewModel.deleteFavorite(favorite)
        changeButtonStatus(false)
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

    private fun checkSavedFavorites() {
        viewModel.getAllFavorite().observe(viewLifecycleOwner, { favList ->
            viewModel.setAllFavorite(favList)
            for (favItem in favList) {
                if (favItem.movieItems.movieId == args.movies.movieId) {
                    this.isFavorite = favItem.favorite
                    viewModel.setIsFavorite(favItem.favorite)
                    viewModel.setFavoriteId(favItem.id)
                    changeButtonStatus(isFavorite)
                } else {
                    viewModel.setIsFavorite(false)
                }
            }
        })
    }

    private fun setFavoriteId() {
        viewModel.favoriteId.observe(viewLifecycleOwner, {
            this.favoriteId = it
        })
    }

    private fun changeButtonStatus(status: Boolean) {
        if (status) {
            movieContentBinding.fabFavorite.setImageResource(R.drawable.ic_add_favorite)
        } else {
            movieContentBinding.fabFavorite.setImageResource(R.drawable.ic_favorite_blank)
        }

    }
}