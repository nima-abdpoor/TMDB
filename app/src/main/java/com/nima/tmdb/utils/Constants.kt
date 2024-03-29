package com.nima.tmdb.utils

import com.bumptech.glide.request.RequestOptions
import com.nima.tmdb.R


object Constants {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    const val API_KEY = "602e06820fbac2f033f50027e0fe5277"
    const val TIME_OUT  : Long = 5
    const val TIME_OUT_SHORT  : Long = 3000L
    const val TIME_OUT_MEDIUM  : Long = 5000L
    const val TIME_OUT_LONG  : Long = 8000L
    const val PADDING  : Int = 10
    const val DEFAULT_LANGUAGE = "en-US"
    const val DEFAULT_REGION = ""
    const val DEFAULT_PAGE = 1
    const val DEFAULT_ADULT = false
    const val DATABASE_NAME = "TMDB_DATABASE"
    const val SHARED_PREFERENCES = "TMDB-sharedPref"
    val DEFAULT_MOVIE_LIST_NAME = arrayOf("life", "toy", "war", "nature", "nina", "big", "peace", "rain", "murder")
    var DEFAULT_IMAGE_REQUEST = RequestOptions().placeholder(R.drawable.ic_launcher_background)
    var DEFAULT_IMAGE = R.drawable.ic_launcher_background.toString()


    //MediaType
    const val ALL_MEDIA_TYPE = "all"
    const val MOVIE_MEDIA_TYPE = "movie"
    const val TV_MEDIA_TYPE = "tv"
    const val PERSON_MEDIA_TYPE = "person"

    //timeWindow
    const val DAY_MEDIA_TYPE = "day"
    const val WEEK_MEDIA_TYPE = "week"
}