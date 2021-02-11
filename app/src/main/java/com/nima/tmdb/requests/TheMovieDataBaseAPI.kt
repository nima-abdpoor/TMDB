package com.nima.tmdb.requests


import com.nima.tmdb.models.Details
import com.nima.tmdb.models.Example
import com.nima.tmdb.models.login.*
import com.nima.tmdb.models.login.account.Account
import com.nima.tmdb.models.movie.popular.PopularInfoModel
import com.nima.tmdb.models.trend.TrendInfoModel
import retrofit2.Response
import retrofit2.http.*

interface TheMovieDataBaseAPI {
    @GET("search/movie")
    suspend fun searchMovieList(
            @Query("api_key") key: String,
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
    ): Response<Details>

    @GET("authentication/token/new")
    suspend fun getNewToken(
        @Query("api_key") key: String?,
    ): Response<Token>

    @POST("authentication/token/validate_with_login")
    suspend fun login(@Body loginInfo : LoginInfo, @Query("api_key") key: String?) : Response<LoginResponse>

    @POST("authentication/session/new")
    suspend fun getSessionId(@Body requestToken : RequestToken, @Query("api_key") key: String?) : Response<Session>

    @GET("account")
    suspend fun getAccountDetails(
        @Query("api_key") key: String,
        @Query("session_id") sessionId: String,
    ): Response<Account>

    @GET("movie/popular")
    suspend fun getPopular(
        @Query("api_key") key: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String="",
    ) : Response<PopularInfoModel>

    @GET("trending/{media_type}/{time_window}")
    suspend fun getTrending(
        @Path("media_type") type: String,
        @Path("time_window") timeWindow: String,
        @Query("api_key") key: String
    ) : Response<TrendInfoModel>
}