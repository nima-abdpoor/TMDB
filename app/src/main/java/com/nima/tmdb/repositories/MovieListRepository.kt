package com.nima.tmdb.repositories

import androidx.lifecycle.LiveData
import com.nima.tmdb.models.Example
//import com.nima.tmdb.requests.MovieAPIClient
import com.nima.tmdb.requests.ServiceGenerator
import com.nima.tmdb.utils.Constants
import kotlinx.coroutines.*

object MovieListRepository {
    var job: CompletableJob? = null

    private var query = ""
    private var pageNumber = 0

    fun searchMovieAPI(query: String, page: Int): LiveData<Example> {
        job = Job()
        this.query = query
        pageNumber = page
        return object : LiveData<Example>() {
            override fun onActive() {
                super.onActive()
                job?.let { theJob ->
                    CoroutineScope(Dispatchers.IO + theJob).launch {
                        val result = ServiceGenerator.apiService().searchMovieList(
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
    }

    fun cancelJob(message : String = "job is canceled!!"){
        job?.cancel(message)
    }

    fun searchNextQuery() {
       // movieAPIClient.searchMovieAPI(query, pageNumber + 1)
    }

}