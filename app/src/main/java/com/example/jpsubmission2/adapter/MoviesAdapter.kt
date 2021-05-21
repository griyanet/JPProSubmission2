package com.example.jpsubmission2.adapter

import android.view.*
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.jpsubmission2.R
import com.example.jpsubmission2.data.remote.responses.MovieResultsItem
import com.example.jpsubmission2.databinding.ItemsMovieBinding
import com.example.jpsubmission2.utils.Constant
import com.example.jpsubmission2.view.ui.movies.MoviesFragmentDirections

class MoviesAdapter: RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<MovieResultsItem>() {
        override fun areItemsTheSame(
            oldItem: MovieResultsItem,
            newItem: MovieResultsItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: MovieResultsItem,
            newItem: MovieResultsItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)
    var movies: List<MovieResultsItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val itemsMovieBinding =
            ItemsMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(itemsMovieBinding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movies = movies[position]
        holder.bind(movies)
    }

    override fun getItemCount(): Int = movies.size

    inner class MoviesViewHolder(private val binding: ItemsMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movies: MovieResultsItem) {
            with(binding) {
                tvTitle.text = movies.originalTitle
                tvMovieDate.text = movies.releaseDate
                tvOverview.text = movies.overview
                itemView.setOnClickListener {
                    val toMovieDetails =
                        MoviesFragmentDirections.actionNavigationHomeToMovieDetailsFragment(movies)
                    itemView.findNavController().navigate(toMovieDetails)
                    //Toast.makeText(itemView.context, movies.original_title + "is click", Toast.LENGTH_SHORT).show()
                }
                val posterImg = Constant.BASE_IMAGE_SMALL + movies.posterPath
                Glide.with(itemView.context)
                    .load(posterImg)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .error(R.drawable.ic_image_error)
                    .into(imgMovie)
            }
        }
    }
}