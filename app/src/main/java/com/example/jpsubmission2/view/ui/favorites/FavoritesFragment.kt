package com.example.jpsubmission2.view.ui.favorites

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jpsubmission2.R
import com.example.jpsubmission2.data.local.entity.FavoritesMovie
import com.example.jpsubmission2.databinding.FragmentFavoritesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private lateinit var favBinding: FragmentFavoritesBinding
    lateinit var viewModel: FavoriteViewModel
    private lateinit var favAdapter: FavoriteMoviesAdapter

    private val swipeCallBack =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val layoutPosition = viewHolder.layoutPosition
                val selectedFavorite = favAdapter.favMovies[layoutPosition]
                viewModel.deleteFavorite(selectedFavorite)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentFavoritesBinding.bind(view)
        favBinding = binding
        viewModel = ViewModelProvider(requireActivity()).get(FavoriteViewModel::class.java)
        setupRecyclerView()
        subscribeToObserver()
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.rvFavorite)
    }

    private fun subscribeToObserver() {
        viewModel.getFavorites().observe(viewLifecycleOwner, {
            Log.d("FavoriteFragment", "Read Favorite list from Network")
            favAdapter.favMovies = it as List<FavoritesMovie>
            favBinding.pbFavorite.visibility = View.GONE
        })
    }

    private fun setupRecyclerView() {
        favAdapter = FavoriteMoviesAdapter()
        with(favBinding.rvFavorite) {
            layoutManager = LinearLayoutManager(context)
            adapter = favAdapter
        }
    }

}