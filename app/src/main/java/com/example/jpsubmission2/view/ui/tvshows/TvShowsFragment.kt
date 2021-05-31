package com.example.jpsubmission2.view.ui.tvshows

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jpsubmission2.R
import com.example.jpsubmission2.adapter.TvShowsAdapter
import com.example.jpsubmission2.data.remote.responses.TvResultsItem
import com.example.jpsubmission2.databinding.FragmentTvshowsBinding
import com.example.jpsubmission2.utils.Status
import com.example.jpsubmission2.utils.snack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowsFragment : Fragment(R.layout.fragment_tvshows) {

    private lateinit var tvShowsBinding: FragmentTvshowsBinding
    lateinit var viewModel: TvShowsViewModel
    private lateinit var tvShowsAdapter: TvShowsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentTvshowsBinding.bind(view)
        tvShowsBinding = binding
        viewModel = ViewModelProvider(requireActivity()).get(TvShowsViewModel::class.java)
        viewModel.getTvs()
        setupRecyclerView()
        subscribeToObserve()
    }

    @Suppress("UNCHECKED_CAST")
    private fun subscribeToObserve() {
        viewModel.tvs.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        result.data?.results.let { resultItem ->
                            tvShowsAdapter.tvShows = resultItem as List<TvResultsItem>
                            tvShowsBinding.pbTvShowsList.visibility = View.GONE
                        }
                    }
                    Status.ERROR -> {
                        tvShowsBinding.tvShowsFragment.snack(R.string.tv_shows_fragment_resource_error)
                        tvShowsBinding.pbTvShowsList.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        tvShowsBinding.pbTvShowsList.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun setupRecyclerView() {
        tvShowsAdapter = TvShowsAdapter()
        with(tvShowsBinding.rvTvShows) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = tvShowsAdapter
        }
    }
}