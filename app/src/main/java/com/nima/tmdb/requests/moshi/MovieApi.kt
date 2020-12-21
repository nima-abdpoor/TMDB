package com.nima.tmdb.requests.moshi

import com.nima.tmdb.models.login.account.Account
import com.nima.tmdb.models.movie.popular.Popular
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi{
    @GET("account")
    suspend fun getAccountDetails(
        @Query("api_key") key: String,
        @Query("session_id") sessionId: String,
    ): Account

    @GET("movie/popular")
    suspend fun getPopular(

    ) : Popular
}