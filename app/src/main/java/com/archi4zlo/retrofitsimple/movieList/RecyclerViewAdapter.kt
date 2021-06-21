package com.archi4zlo.retrofitsimple.movieList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.archi4zlo.retrofitsimple.R
import com.archi4zlo.retrofitsimple.data.remote.movie.Movie
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recyclerview_item_movie.view.*
import java.util.*

class RecyclerViewAdapter(private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<RecyclerViewAdapter.MovieViewHolder>() {

    var items = ArrayList<Movie>()

    fun setListData(data: ArrayList<Movie>) {
        this.items = data
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item_movie, parent, false)
        return MovieViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.MovieViewHolder, position: Int) {
        val movie = items[position]
        holder.itemView.setOnClickListener {
            onClickListener.onClick(movie)
        }
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvTitle = view.overview_title
        val imagePoster = view.overview_image

        fun bind(data: Movie) {
            tvTitle.text = data.original_title


            val url = "https://image.tmdb.org/t/p/w500/" + data.poster_path
            Glide.with(imagePoster)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.default_thumb)
                .error(R.drawable.default_thumb)
                .fallback(R.drawable.default_thumb)
                .into(imagePoster)
        }
    }

    class OnClickListener(val clickListener: (movie: Movie) -> Unit) {
        fun onClick(movie: Movie) = clickListener(movie)
    }
}