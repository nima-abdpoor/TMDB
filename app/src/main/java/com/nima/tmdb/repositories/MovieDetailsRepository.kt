package com.nima.tmdb.repositories

import androidx.lifecycle.LiveData
import com.nima.tmdb.models.Details
import com.nima.tmdb.models.Example
import com.nima.tmdb.requests.ServiceGenerator
import com.nima.tmdb.utils.Constants
import kotlinx.coroutines.*


object MovieDetailsRepository  {

    var job: CompletableJob? = null

    private var movieID : Int = 0
    private var pageNumber = 0

    fun searchMovieAPI(movieID : Int): LiveData<Details> {
        job = Job()
        this.movieID =movieID
        return object : LiveData<Details>() {
            override fun onActive() {
                super.onActive()
                job?.let { theJob ->
                    CoroutineScope(Dispatchers.IO + theJob).launch {
                       val details = ServiceGenerator.apiService().getMovieDetails(
                           movieID,
                           Constants.API_KEY,
                           Constants.DEFAULT_LANGUAGE
                       )
                        withContext(Dispatchers.Main) {
                            value = details
                            theJob.complete()
                        }
                    }
                }
            }
        }
    }

    fun cancelJob(message : String = "job is canceled!!"){
        job?.cancel(message)
    }
}