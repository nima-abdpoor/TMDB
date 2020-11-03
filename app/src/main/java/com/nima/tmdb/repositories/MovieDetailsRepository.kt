package com.nima.tmdb.repositories

import androidx.lifecycle.LiveData
import com.nima.tmdb.models.Details
import com.nima.tmdb.requests.MovieAPIClient


//
class MovieDetailsRepository private constructor() {
    private val movieAPIClient: MovieAPIClient = MovieAPIClient()
    val isRequestTimeOut: LiveData<Boolean>
        get() = movieAPIClient.isRequestTimeOut()
    val movieDetails: LiveData<Details?>
        get() = movieAPIClient.movieDetails

    fun searchMovieDetails(movieID: Int) {
        movieAPIClient.searchMovieID(movieID)
    }

    companion object {
        var instance: MovieDetailsRepository? = null
            get() {
                if (field == null) {
                    field = MovieDetailsRepository()
                }
                return field
            }
            private set
    }

}