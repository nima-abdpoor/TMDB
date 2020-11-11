package com.nima.tmdb.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.nima.tmdb.models.Example
import com.nima.tmdb.requests.ServiceGenerator
import com.nima.tmdb.utils.Constants
import com.nima.tmdb.utils.Resource
import kotlinx.coroutines.*
import retrofit2.Response
import java.io.IOException

object MovieListRepository {

    lateinit var result : Response<Example>
    const val TAG = "MovieListRepository"

    var job: CompletableJob? = null

    private var query = ""
    private var pageNumber = 0

    fun searchMovieAPI(query: String, page: Int): LiveData<Resource<Example>> {
        job = Job()
        this.query = query
        pageNumber = page
        return object : LiveData<Resource<Example>>() {
            override fun onActive() {
                super.onActive()
                value = Resource.loading()
                job?.let { theJob ->
                    CoroutineScope(Dispatchers.IO + theJob).launch {
                        try {
                             result = ServiceGenerator.apiService().searchMovieList(
                                Constants.API_KEY,
                                Constants.DEFAULT_LANGUAGE,
                                query,
                                page,
                                Constants.DEFAULT_ADULT
                            )
                            withContext(Dispatchers.Main) {
                                value = if (result.isSuccessful) {
                                    Resource.success(result.body())
                                } else {
                                    Resource.error(result.message())
                                }
                                theJob.complete()
                            }
                        }
                        catch (e : IOException){
                            withContext(Dispatchers.Main){
                                value = Resource.error(e.toString())
                                Log.d(TAG, "onActive: ${e.message}")
                            }
                        }
                    }
                }
            }
        }
    }


    fun cancelJob(message: String = "job is canceled!!") {
        job?.cancel(message)
    }

    fun searchNextQuery() {
        // movieAPIClient.searchMovieAPI(query, pageNumber + 1)
    }
}