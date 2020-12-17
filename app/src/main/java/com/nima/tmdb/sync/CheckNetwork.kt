package com.nima.tmdb.sync

import android.util.Log
import com.nima.tmdb.requests.ServiceGenerator
import com.nima.tmdb.utils.Constants.API_KEY
import com.nima.tmdb.utils.Constants.TIME_OUT_SHORT
import kotlinx.coroutines.withTimeoutOrNull

class CheckNetwork() {
    suspend fun getRequestToken(): SyncStates {
        return withTimeoutOrNull(TIME_OUT_SHORT) {
            val token = ServiceGenerator.apiService().getNewToken(API_KEY)
            Log.d("TAG", "authentication: ${token.requestToken}")
            token
        }?.let { token ->
            return if (token.success)
                SyncStates.Success(token.requestToken!!)
            else
                SyncStates.Failed(
                    token.statusCode!!,
                    token.statusMessage!!
                )
        } ?: let {
            return SyncStates.TimeOutError("Check Your Internet Connection!")
        }
    }
}
