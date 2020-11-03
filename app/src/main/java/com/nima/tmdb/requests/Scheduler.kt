package com.nima.tmdb.requests

import androidx.lifecycle.MutableLiveData
import com.nima.tmdb.executors.AppExecutors
import com.nima.tmdb.utils.Constants
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

class Scheduler {
    fun schedule(future: Future<*>, RequestTimeOut: MutableLiveData<Boolean>) {
        AppExecutors.executorService.schedule({
            RequestTimeOut.postValue(true)
            future.cancel(true)
        }, Constants.TIME_OUT, TimeUnit.SECONDS)
    }
}