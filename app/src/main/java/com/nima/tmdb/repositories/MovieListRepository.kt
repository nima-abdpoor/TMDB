package com.nima.tmdb.repositories

//import com.nima.tmdb.requests.MovieAPIClient
import androidx.lifecycle.LiveData
import com.nima.tmdb.models.Example
import com.nima.tmdb.requests.ServiceGenerator
import com.nima.tmdb.utils.Constants
import kotlinx.coroutines.*
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object MovieListRepository {
    const val  TAG = "MovieListRepository"
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
                                try {
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
                                catch (e : SocketTimeoutException){
                                    var example = Example()
                                    example.error = "Connection Timed Out!!"
                                    withContext(Dispatchers.Main) {
                                        value = example
                                        theJob.complete()
                                    }
                                }
                                catch (e : UnknownHostException){
                                    var example = Example()
                                    example.error = "No Internet Connection!"
                                    withContext(Dispatchers.Main) {
                                        value = example
                                        theJob.complete()
                                    }
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