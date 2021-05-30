package com.example.jpsubmission2.view.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.jpsubmission2.R
import com.example.jpsubmission2.data.local.entity.FavoritesMovie
import com.example.jpsubmission2.databinding.ItemsMovieBinding
import com.example.jpsubmission2.utils.Constant

class FavoriteMoviesAdapter :
    RecyclerView.Adapter<FavoriteMoviesAdapter.FavoriteMoviesViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<FavoritesMovie>() {
        override fun areItemsTheSame(
            oldItem: FavoritesMovie,
            newItem: FavoritesMovie
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: FavoritesMovie, newItem: FavoritesMovie): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)
    var favMovies: List<FavoritesMovie>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMoviesViewHolder {
        val itemsMovieBinding =
            ItemsMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteMoviesViewHolder(itemsMovieBinding)
    }

    override fun onBindViewHolder(holder: FavoriteMoviesViewHolder, position: Int) {
        val movies = favMovies[position]
        holder.bind(movies)
    }

    override fun getItemCount(): Int = favMovies.size

    class FavoriteMoviesViewHolder(private val binding: ItemsMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favMovies: FavoritesMovie) {
            val favMoviesItem = favMovies.movieItems
            val favoriteId = favMovies.id
            with(binding) {
                tvTitle.text = favMovies.movieItems.originalTitle
                tvMovieDate.text = favMovies.movieItems.releaseDate
                tvOverview.text = favMovies.movieItems.overview
                itemView.setOnClickListener {
                    val toFavMovieDetails =
                        FavoritesFragmentDirections.actionFavoritesToMovieDetailsFragment(
                            favMoviesItem, favoriteId
                        )
                    itemView.findNavController().navigate(toFavMovieDetails)
                }
                val posterImg = Constant.BASE_IMAGE_SMALL + favMovies.movieItems.posterPath
                Glide.with(itemView.context)
                    .load(posterImg)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .error(R.drawable.ic_image_error)
                    .into(imgMovie)
            }
        }
    }
}