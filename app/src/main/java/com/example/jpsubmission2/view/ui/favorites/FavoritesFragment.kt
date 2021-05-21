package com.example.jpsubmission2.view.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jpsubmission2.databinding.FragmentFavoritesBinding
import com.example.jpsubmission2.viewmodel.FavoritesViewModel

class FavoritesFragment : Fragment() {

    private lateinit var favBinding: FragmentFavoritesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favBinding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        return favBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FavoritesViewModel::class.java]
        val favMovie = viewModel.getFavorites()

        val favAdapter = FavoriteMoviesAdapter()
        favAdapter.setMovies(favMovie)

        with(favBinding.rvFavorite) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = favAdapter
        }

        favBinding.progressBar.visibility = View.GONE
    }



}