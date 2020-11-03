package com.nima.tmdb.adapters

interface OnMovieListener {
    fun onMovieClick(position: Int)
    fun onCategoryClick(category: String?)
}