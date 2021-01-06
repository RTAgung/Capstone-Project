package com.example.moviecatalogue.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviecatalogue.core.R
import com.example.moviecatalogue.core.databinding.ItemMovieBinding
import com.example.moviecatalogue.core.domain.model.Movie

class MovieAdapter(val itemClickCallback: ItemClickCallback) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movieList = ArrayList<Movie>()

    fun setMovieList(newMovieList: List<Movie>?) {
        if (newMovieList == null) return
        movieList.clear()
        movieList.addAll(newMovieList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMovieBinding.bind(itemView)
        private val imageUrl = "https://image.tmdb.org/t/p/w500/"

        fun bind(data: Movie) {
            with(binding) {
                tvItemOriginalTitle.text = data.originalTitle
                tvItemTitle.text = data.title
                tvItemRate.text = data.voteAverage.toString()
                Glide.with(itemView.context)
                    .load(imageUrl + data.posterPath)
                    .apply {
                        RequestOptions.placeholderOf(R.drawable.ic_error)
                            .error(R.drawable.ic_error)
                    }
                    .into(ivItemPoster)
            }

            itemView.setOnClickListener {
                itemClickCallback.onItemClicked(data)
            }
        }
    }

    interface ItemClickCallback {
        fun onItemClicked(data: Movie)
    }
}
