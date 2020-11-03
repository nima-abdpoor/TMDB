package com.nima.tmdb.adapters

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.nima.tmdb.R


class MovieViewHolder(itemView: View, onMovieListener: OnMovieListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    var title: TextView = itemView.findViewById(R.id.movie_title)
    var description: TextView = itemView.findViewById(R.id.movie_description)
    var releaseDate: TextView = itemView.findViewById(R.id.release_date)
    var error: TextView? = null
    var image: AppCompatImageView = itemView.findViewById(R.id.movie_image)
    var onMovieListener: OnMovieListener = onMovieListener
    override fun onClick(v: View) {
        onMovieListener.onMovieClick(adapterPosition)
    }

    init {
        itemView.setOnClickListener { v: View -> onClick(v) }
    }
}