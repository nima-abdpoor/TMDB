package com.nima.tmdb.repositories

import androidx.lifecycle.LiveData
import com.nima.tmdb.models.Details
import com.nima.tmdb.models.Result
import com.nima.tmdb.requests.MovieAPIClient

object MovieRepository  {
    private val movieAPIClient: MovieAPIClient = MovieAPIClient()
    private var  query = ""
    private var pageNumber = 0
    val movies: LiveData<List<Result>>
        get() = movieAPIClient.movies
    val movieDetails: LiveData<Details?>
        get() = movieAPIClient.movieDetails

    fun searchMovieAPI(query: String, page: Int) {
        this.query = query
        pageNumber = page
        movieAPIClient.searchMovieAPI(query, page)
    }

    fun searchMovieDetails(movieID: Int) {
        movieAPIClient.searchMovieID(movieID)
    }

    fun searchNextQuery() {
        movieAPIClient.searchMovieAPI(query, pageNumber + 1)
    }

    val isRequestTimedOut: LiveData<Boolean>
        get() = movieAPIClient.isRequestTimeOut()



}