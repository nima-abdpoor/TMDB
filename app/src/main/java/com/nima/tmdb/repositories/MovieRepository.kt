package com.nima.tmdb.repositories

import androidx.lifecycle.LiveData
import com.nima.tmdb.models.Details
import com.nima.tmdb.models.Example
import com.nima.tmdb.models.Result
//import com.nima.tmdb.requests.MovieAPIClient
import com.nima.tmdb.requests.ServiceGenerator
import com.nima.tmdb.utils.Constants
import kotlinx.coroutines.*

object MovieRepository {
    var job: CompletableJob? = null

    //private val movieAPIClient: MovieAPIClient = MovieAPIClient()
    private var query = ""
    private var pageNumber = 0
//    val movies: LiveData<List<Result>>
//        get() = movieAPIClient.movies
//    val movieDetails: LiveData<Details?>
//        get() = movieAPIClient.movieDetails

    fun searchMovieAPI(query: String, page: Int): LiveData<Example> {
        job = Job()
        this.query = query
        pageNumber = page
        return object : LiveData<Example>() {
            override fun onActive() {
                super.onActive()
                job?.let { theJob ->
                    CoroutineScope(Dispatchers.IO + theJob).launch {
                        val result = ServiceGenerator.getMovies().searchResponse(
                            Constants.API_KEY,
                            Constants.DEFAULT_LANGUAGE,
                            query,
                            page,
                            Constants.DEFAULT_ADULT
                        )
                        withContext(Dispatchers.Main) {
                            value = result
                            theJob.complete()
                        }
                    }
                }
            }
        }
        //movieAPIClient.searchMovieAPI(query, page)
    }

    fun cancelJob(message : String = "job is canceled!!"){
        job?.cancel(message)
    }

    fun searchMovieDetails(movieID: Int) {
        //movieAPIClient.searchMovieID(movieID)
    }

    fun searchNextQuery() {
       // movieAPIClient.searchMovieAPI(query, pageNumber + 1)
    }

    //val isRequestTimedOut: LiveData<Boolean>
       // get() = movieAPIClient.isRequestTimeOut()


}