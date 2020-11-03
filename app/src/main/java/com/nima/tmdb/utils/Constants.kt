package com.nima.tmdb.utils

import com.bumptech.glide.request.RequestOptions
import com.nima.tmdb.R


object Constants {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    const val API_KEY = "602e06820fbac2f033f50027e0fe5277"
    const val TIME_OUT  : Long = 30
    const val DEFAULT_LANGUAGE = "en-US"
    const val DEFAULT_PAGE = 1
    const val DEFAULT_ADULT = false
    val DEFAULT_MOVIE_LIST_NAME = arrayOf("life", "toy", "war", "nature", "nina", "big", "peace", "rain", "murder")
    var DEFAULT_IMAGE_REQUEST = RequestOptions().placeholder(R.drawable.ic_launcher_background)
    var DEFAULT_IMAGE = R.drawable.ic_launcher_background.toString()
}