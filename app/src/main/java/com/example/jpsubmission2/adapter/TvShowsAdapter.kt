package com.example.jpsubmission2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.jpsubmission2.R
import com.example.jpsubmission2.data.remote.responses.TvResultsItem
import com.example.jpsubmission2.databinding.ItemsTvShowsBinding
import com.example.jpsubmission2.utils.Constant
import com.example.jpsubmission2.view.ui.tvshows.TvShowsFragmentDirections

class TvShowsAdapter: RecyclerView.Adapter<TvShowsAdapter.TvShowsViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<TvResultsItem>() {
        override fun areItemsTheSame(oldItem: TvResultsItem, newItem: TvResultsItem): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: TvResultsItem, newItem: TvResultsItem): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var tvShows: List<TvResultsItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowsViewHolder {
        val itemsTvShowsBinding = ItemsTvShowsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowsViewHolder(itemsTvShowsBinding)
    }

    override fun onBindViewHolder(holder: TvShowsViewHolder, position: Int) {
        val tvShows = tvShows[position]
        holder.bind(tvShows)
    }

    override fun getItemCount(): Int = tvShows.size

    class TvShowsViewHolder(private val binding: ItemsTvShowsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShows: TvResultsItem) {
            with(binding) {
                tvTvShowTitle.text = tvShows.originalName
                tvTvShowYear.text = tvShows.firstAirDate
                tvTvShowsOverview.text = tvShows.overview
                itemView.setOnClickListener {
                    val toTvShowsDetails = TvShowsFragmentDirections.actionTvShowsToTvShowsDetailsFragment(tvShows)
                    itemView.findNavController().navigate(toTvShowsDetails)
//                    Toast.makeText(itemView.context, tvShows.title + "is click", Toast.LENGTH_SHORT).show()
                }
                val posterImg = Constant.BASE_IMAGE_SMALL + tvShows.posterPath
                Glide.with(itemView.context)
                    .load(posterImg)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .error(R.drawable.ic_image_error)
                    .into(imgTvShow)
            }
        }
    }


}