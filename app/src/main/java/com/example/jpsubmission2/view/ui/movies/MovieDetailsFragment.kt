package com.example.jpsubmission2.view.ui.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.jpsubmission2.R
import com.example.jpsubmission2.data.remote.responses.MovieDetailResponse
import com.example.jpsubmission2.databinding.FragmentMovieDetailsBinding
import com.example.jpsubmission2.utils.Constant
import com.example.jpsubmission2.utils.Status
import com.example.jpsubmission2.utils.snack
import com.example.jpsubmission2.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment @Inject constructor(
    private val glide: RequestManager
): Fragment(R.layout.fragment_movie_details) {

    private lateinit var movieDetail: FragmentMovieDetailsBinding
    private val args by navArgs<MovieDetailsFragmentArgs>()
    lateinit var viewModel: MainViewModel

   /* override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        movieDetail = FragmentMovieDetailsBinding.inflate(layoutInflater, container, false)
        return movieDetail.root
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        viewModel.movieSelected(args.movies)
        viewModel.getMovieDetails()
        viewModel.movieDetails.observe(viewLifecycleOwner, {
            Log.d("MovieDetailFragment", "Read Movie Detail from Network")
            it.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        result.data?.let { movieDetails ->
                            populateMovie(movieDetails)
                        }
                        movieDetail.progressBar.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        movieDetail.movieDetail.snack(R.string.movie_detail_fragment_resource_error)
                        Snackbar.make(
                            requireView().rootView,
                            result.message ?: "An unknown error occurred",
                            Snackbar.LENGTH_LONG).show()
                        movieDetail.progressBar.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        movieDetail.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })

        movieDetail.detailContent.fabFavorite.setOnClickListener {
            movieDetail.movieDetail.snack(R.string.addFavorite)
        }
    }

    private fun populateMovie(result: MovieDetailResponse) {
        val posterImg = Constant.BASE_IMAGE_SMALL + result.posterPath
        val backdropImg = Constant.BASE_IMAGE_LARGE + result.backdropPath
        with(movieDetail.detailContent) {
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