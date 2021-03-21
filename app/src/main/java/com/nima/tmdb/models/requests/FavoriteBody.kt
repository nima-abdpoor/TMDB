package com.nima.tmdb.models.requests

data class FavoriteBody(
    val media_type : String,
    val media_id : Int,
    val favorite : Boolean
){
    override fun toString(): String {
        return "FavoriteBody" +
                "(" +
                "media_type='$media_type'," +
                " media_id=$media_id, " +
                "favorite=$favorite" +
                ")"
    }
}