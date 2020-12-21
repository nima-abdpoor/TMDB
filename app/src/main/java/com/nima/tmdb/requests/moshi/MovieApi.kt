package com.nima.tmdb.requests.moshi

import com.nima.tmdb.models.login.Token
import com.nima.tmdb.models.login.account.Account
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi{
    @GET("account")
    suspend fun getAccountDetails(
        @Query("api_key") key: String,
        @Query("session_id") sessionId: String,
    ): Account

    @GET("authentication/token/new")
    suspend fun getNewToken(
        @Query("api_key") key: String?,
    ): Token
}