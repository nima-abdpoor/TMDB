package com.nima.tmdb.models.requests

data class FavoriteBody(
    val media_type : String,
    val media_id : Int,
    val favorite : Boolean
)