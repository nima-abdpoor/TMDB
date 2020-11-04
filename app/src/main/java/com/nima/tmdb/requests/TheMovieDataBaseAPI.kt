package com.nima.tmdb.requests


import com.nima.tmdb.models.Details
import com.nima.tmdb.models.Example
import com.nima.tmdb.models.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDataBaseAPI {
    @GET("search/movie")
    suspend fun searchMovieList(
            @Query("api_key") key: String?,
            @Query("language") language: String?,
            @Query("query") query: String?,
            @Query("page") page: Int,
            @Query("include_adult") include_adult: Boolean
    ): Example

    @GET("movie/{movieID}")
    suspend fun getMovieDetails(
            @Path("movieID") movieID: Int,
            @Query("api_key") key: String?,
            @Query("language") language: String?
    ): Details
}