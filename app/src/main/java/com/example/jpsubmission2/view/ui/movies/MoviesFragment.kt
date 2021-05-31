package com.example.jpsubmission2.view.ui.movies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jpsubmission2.R
import com.example.jpsubmission2.adapter.MoviesAdapter
import com.example.jpsubmission2.data.remote.responses.MovieResultsItem
import com.example.jpsubmission2.databinding.FragmentMoviesBinding
import com.example.jpsubmission2.utils.EspressoIdlingResource
import com.example.jpsubmission2.utils.Status
import com.example.jpsubmission2.utils.snack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private lateinit var _fragmentMoviesBinding: FragmentMoviesBinding
    private val fragmentMoviesBinding get() = _fragmentMoviesBinding
    lateinit var viewModel: MovieViewModel
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMoviesBinding.bind(view)
        _fragmentMoviesBinding = binding
        viewModel = ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        viewModel.getMovies()
        setupRecyclerView()
        subscribeToObserver()
    }

    private fun setupRecyclerView() {
        moviesAdapter = MoviesAdapter()
        with(fragmentMoviesBinding.rvMovie) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = moviesAdapter
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun subscribeToObserver() {
        EspressoIdlingResource.increment()
        viewModel.movies.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled().let { result ->
                when (result?.status) {
                    Status.SUCCESS -> {
                        result.data?.results.let { resultItem ->
                            moviesAdapter.movies = resultItem as List<MovieResultsItem>
                            fragmentMoviesBinding.pbMovies.visibility = View.GONE
                        }
                    }
                    Status.ERROR -> {
                        fragmentMoviesBinding.fragmentMovies.snack(R.string.movie_fragment_resource_error)
                        fragmentMoviesBinding.pbMovies.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        fragmentMoviesBinding.pbMovies.visibility = View.VISIBLE
                    }
                }
            }
        })
        EspressoIdlingResource.decrement()
    }
}