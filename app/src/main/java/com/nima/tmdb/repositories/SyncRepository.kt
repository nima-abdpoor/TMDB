package com.nima.tmdb.repositories

import com.nima.tmdb.models.login.Token
import com.nima.tmdb.requests.moshi.Network
import com.nima.tmdb.utils.Constants.API_KEY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

object SyncRepository {
    private lateinit var job : Job
    private lateinit var token: Token
    suspend fun requestToken(): Token? {
        try {
             job = CoroutineScope(Dispatchers.IO).launch {
                token = Network.service.getNewToken(API_KEY)
            }
        } finally {
            job.cancel()
        }
        return null
    }
}