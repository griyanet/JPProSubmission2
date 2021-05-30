package com.example.jpsubmission2.view.ui.tvshows

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.example.jpsubmission2.R
import com.example.jpsubmission2.data.remote.responses.TvDetailResponse
import com.example.jpsubmission2.databinding.FragmentTvContentBinding
import com.example.jpsubmission2.databinding.FragmentTvShowsDetailsBinding
import com.example.jpsubmission2.utils.Constant
import com.example.jpsubmission2.utils.Status
import com.example.jpsubmission2.utils.snack
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TvShowsDetailsFragment @Inject constructor(
    private val glide: RequestManager
) : Fragment(R.layout.fragment_tv_shows_details) {

    private lateinit var tvShowsDetailBinding: FragmentTvShowsDetailsBinding
    private lateinit var tvShowsContentBinding: FragmentTvContentBinding
    private val args by navArgs<TvShowsDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentTvShowsDetailsBinding.bind(view)
        tvShowsDetailBinding = binding
        tvShowsContentBinding = tvShowsDetailBinding.tvContentFragment
        val viewModel = ViewModelProvider(requireActivity()).get(TvShowDetailsViewModel::class.java)
        viewModel.tvSelected(args.tvShows)
        viewModel.getTvDetails()
        viewModel.tvDetails.observe(viewLifecycleOwner, {
            Log.d("TvDetailFragment", "Read Movie Detail from Network")
            it.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        result.data?.let { movieDetails ->
                            populateTv(movieDetails)
                        }
                        tvShowsDetailBinding.pbTvShows.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        tvShowsDetailBinding.tvShowDetailFragment.snack(R.string.tv_shows_detail_fragment_resource_error)
                        tvShowsDetailBinding.pbTvShows.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        tvShowsDetailBinding.pbTvShows.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun populateTv(result: TvDetailResponse) {
        val posterImg = Constant.BASE_IMAGE_SMALL + result.posterPath
        val backdropImg = Constant.BASE_IMAGE_LARGE + result.backdropPath
        with(tvShowsContentBinding) {
            glide.load(posterImg).into(imgTvShowsPoster)
            glide.load(backdropImg).into(imgTvShowsBackdrop)
            tvTvShowsTitle.text = result.originalName
            tvTvShowsReleaseYear.text = result.firstAirDate
            tvTvShowSynopsis.text = result.overview
        }
    }
}