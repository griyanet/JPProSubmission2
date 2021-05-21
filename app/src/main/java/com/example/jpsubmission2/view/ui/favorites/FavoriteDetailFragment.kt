package com.example.jpsubmission2.view.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.jpsubmission2.databinding.FragmentFavoriteDetailBinding
import dagger.hilt.android.AndroidEntryPoint


class FavoriteDetailFragment : Fragment() {

    private var _binding: FragmentFavoriteDetailBinding? = null
    private val binding get() = _binding!!
    //private val args by navArgs<FavoriteDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[FavoriteDetailViewModel::class.java]*/



        /*with(binding) {
            favImgBackdrop.setImageResource(favMovie.image)
            favImgPoster.setImageResource(favMovie.image)
            tvFavTitle.text = favMovie.original_title
            tvFavReleaseYear.text = favMovie.release_date
            tvFavBudget.text = favMovie.budget.toString()
            tvFavRevenue.text = favMovie.revenue.toString()
            tvFavSynopsis.text = favMovie.overview
        }*/
    }

}