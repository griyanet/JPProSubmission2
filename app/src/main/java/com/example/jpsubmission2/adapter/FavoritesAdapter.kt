package com.example.jpsubmission2.view.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.jpsubmission2.R
import com.example.jpsubmission2.data.local.FavoritesMovie
import com.example.jpsubmission2.databinding.ItemsMovieBinding

class FavoriteMoviesAdapter: RecyclerView.Adapter<FavoriteMoviesAdapter.FavoriteMoviesViewHolder>() {

    private var listFavMovies = ArrayList<FavoritesMovie>()

    fun setMovies(favMovies: List<FavoritesMovie>?) {
        if (favMovies == null) return
        this.listFavMovies.clear()
        this.listFavMovies.addAll(favMovies)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMoviesViewHolder {
        val itemsMovieBinding =
            ItemsMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteMoviesViewHolder(itemsMovieBinding)
    }

    override fun onBindViewHolder(holder: FavoriteMoviesViewHolder, position: Int) {
        val movies = listFavMovies[position]
        holder.bind(movies)
    }

    override fun getItemCount(): Int = listFavMovies.size

    class FavoriteMoviesViewHolder(private val binding: ItemsMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favMovies: FavoritesMovie) {
            with(binding) {
                tvTitle.text = favMovies.original_title
                tvMovieDate.text = favMovies.release_date
                tvOverview.text = favMovies.overview
                itemView.setOnClickListener {
                    val toFavMovieDetails = FavoritesFragmentDirections.actionFavoritesToFavoriteDetailFragment()
                    itemView.findNavController().navigate(toFavMovieDetails)
                    Toast.makeText(
                        itemView.context,
                        favMovies.original_title + "is click",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Glide.with(itemView.context)
                    .load(favMovies.image)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .error(R.drawable.ic_image_error)
                    .into(imgMovie)
            }
        }
    }
}