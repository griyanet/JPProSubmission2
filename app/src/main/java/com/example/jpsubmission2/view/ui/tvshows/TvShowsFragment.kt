package com.example.jpsubmission2.view.ui.tvshows

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jpsubmission2.R
import com.example.jpsubmission2.adapter.TvShowsAdapter
import com.example.jpsubmission2.data.remote.responses.TvResultsItem
import com.example.jpsubmission2.databinding.FragmentTvshowsBinding
import com.example.jpsubmission2.utils.Status
import com.example.jpsubmission2.utils.snack
import com.example.jpsubmission2.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TvShowsFragment @Inject constructor(
    private val tvShowsAdapter: TvShowsAdapter) : Fragment(R.layout.fragment_tvshows) {

    private lateinit var tvShowsBinding: FragmentTvshowsBinding
    lateinit var viewModel: MainViewModel

   /* override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        tvShowsBinding = FragmentTvshowsBinding.inflate(layoutInflater, container, false)
        return tvShowsBinding.root
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        viewModel.getTvs()
        setupRecyclerView()
        subscribeToObserve()
    }

    @Suppress("UNCHECKED_CAST")
    private fun subscribeToObserve() {
        viewModel.tvs.observe(viewLifecycleOwner, {
            Log.d("TvShowsFragment", "Read data from network")
            it.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        result.data?.results.let { resultItem ->
                            tvShowsAdapter.tvShows = resultItem as List<TvResultsItem>
                            tvShowsBinding.progressBar.visibility = View.GONE
                        }
                    }
                    Status.ERROR -> {
                        tvShowsBinding.tvShowFragment.snack(R.string.tv_shows_fragment_resource_error)
                        tvShowsBinding.progressBar.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        tvShowsBinding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun setupRecyclerView() {
        with(tvShowsBinding.rvTvShows) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = tvShowsAdapter
        }
    }
}