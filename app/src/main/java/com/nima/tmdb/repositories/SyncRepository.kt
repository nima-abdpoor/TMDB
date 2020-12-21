package com.nima.tmdb.repositories

import android.util.Log
import com.nima.tmdb.models.login.Token
import com.nima.tmdb.requests.ServiceGenerator
import com.nima.tmdb.requests.moshi.Network
import com.nima.tmdb.utils.Constants.API_KEY
import com.nima.tmdb.utils.Constants.TIME_OUT_SHORT
import kotlinx.coroutines.*

const val TAG  =  "SyncRepository"
object SyncRepository {
    private lateinit var token: Token
    suspend fun requestToken(): Token? {
        withTimeoutOrNull(TIME_OUT_SHORT){
            token = ServiceGenerator.apiService().getNewToken(API_KEY)
            Log.d(TAG, "requestToken: $token")
        }
        token?.let {
            return token
        }
    }
}