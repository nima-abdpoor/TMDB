package com.nima.tmdb.repositories

import com.nima.tmdb.requests.moshi.Network
import com.nima.tmdb.utils.Constants.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object MainPageRepository {
    suspend fun getAccountDetails(sessionId: String) {
        withContext(Dispatchers.IO){
            Network.service.getAccountDetails(API_KEY , sessionId).await()
        }
    }
}