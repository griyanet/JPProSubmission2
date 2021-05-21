package com.example.jpsubmission2.view.ui.movies

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jpsubmission2.R
import com.example.jpsubmission2.adapter.MoviesAdapter
import com.example.jpsubmission2.data.remote.responses.MovieResultsItem
import com.example.jpsubmission2.databinding.FragmentMoviesBinding
import com.example.jpsubmission2.utils.Status
import com.example.jpsubmission2.utils.snack
import com.example.jpsubmission2.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment @Inject constructor(
    private val moviesAdapter: MoviesAdapter
) : Fragment(R.layout.fragment_movies) {

    private lateinit var _fragmentMoviesBinding: FragmentMoviesBinding
    private val fragmentMoviesBinding get() = _fragmentMoviesBinding
    lateinit var viewModel: MainViewModel

    /*override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentMoviesBinding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
        return _fragmentMoviesBinding.root
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        viewModel.getMovies()
        setupRecyclerView()
        subscribeToObserver()
    }

    private fun setupRecyclerView() {
        with(fragmentMoviesBinding.rvMovie) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = moviesAdapter
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun subscribeToObserver() {
        viewModel.movies.observe(viewLifecycleOwner, {
            Log.d("MoviesFragment", "Read data from network")
            it.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        result.data?.results.let { resultItem ->
                            moviesAdapter.movies = resultItem as List<MovieResultsItem>
                            fragmentMoviesBinding.progressBar.visibility = View.GONE
                        }
                    }
                    Status.ERROR -> {
                        fragmentMoviesBinding.fragmentMovie.snack(R.string.movie_fragment_resource_error)
                        fragmentMoviesBinding.progressBar.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        fragmentMoviesBinding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }
}