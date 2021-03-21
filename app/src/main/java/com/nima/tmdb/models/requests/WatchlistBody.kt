package com.nima.tmdb.models.requests

class WatchlistBody(
    private val media_type : String,
    private val media_id : Int,
    private val watchlist : Boolean
) {
    override fun toString(): String {
        return "WatchlistBody(" +
                "media_type='$media_type'," +
                " media_id=$media_id, " +
                "watchlist='$watchlist'" +
                ")"
    }
}