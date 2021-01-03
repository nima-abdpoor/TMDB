package com.nima.tmdb.repositories

import android.util.Log
import com.nima.tmdb.requests.moshi.Network
import com.nima.tmdb.utils.Constants.API_KEY
import com.nima.tmdb.utils.Constants.DEFAULT_LANGUAGE
import com.nima.tmdb.utils.Constants.DEFAULT_PAGE
import com.nima.tmdb.utils.Constants.DEFAULT_REGION
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object MovieRepository {
    fun getPopularMovies(){
        CoroutineScope(Dispatchers.IO).launch {
            val pupular  =Network.service.getPopular(
                API_KEY, DEFAULT_LANGUAGE, DEFAULT_PAGE, DEFAULT_REGION)
            Log.d("aklsdjfalsd;f", "getPopularMovies: ${pupular?.results?.get(1)?.id}")
        }
    }
}