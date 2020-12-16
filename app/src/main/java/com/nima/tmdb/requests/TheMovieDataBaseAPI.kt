package com.nima.tmdb.requests


import com.nima.tmdb.models.*
import com.nima.tmdb.models.login.*
import com.nima.tmdb.models.login.account.Account
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface TheMovieDataBaseAPI {
    @GET("search/movie")
    suspend fun searchMovieList(
            @Query("api_key") key: String?,
            @Query("language") language: String?,
            @Query("query") query: String?,
            @Query("page") page: Int,
            @Query("include_adult") include_adult: Boolean
    ): Response<Example>

    @GET("movie/{movieID}")
    suspend fun getMovieDetails(
            @Path("movieID") movieID: Int,
            @Query("api_key") key: String?,
            @Query("language") language: String?
    ): Details

    @GET("authentication/token/new")
    suspend fun getNewToken(
        @Query("api_key") key: String?,
    ): Token

    @POST("authentication/token/validate_with_login")
    suspend fun login(@Body loginInfo : LoginInfo, @Query("api_key") key: String?) : LoginResponse

    @POST("authentication/session/new")
    suspend fun getSessionId(@Body requestToken : RequestToken, @Query("api_key") key: String?) : Session

    @GET("account")
    suspend fun getAccountDetails(
        @Query("api_key") key: String,
        @Query("session_id") sessionId: String,
    ): Account
}