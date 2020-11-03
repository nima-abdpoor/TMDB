package com.nima.tmdb.requests


import com.nima.tmdb.models.Details
import com.nima.tmdb.models.Example
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDataBaseAPI {
    @GET("search/movie")
    fun searchResponse(
            @Query("api_key") key: String?,
            @Query("language") language: String?,
            @Query("query") query: String?,
            @Query("page") page: Int,
            @Query("include_adult") include_adult: Boolean
    ): Call<Example>

    @GET("movie/{movieID}")
    fun getMovieDetails(
            @Path("movieID") movieID: Int,
            @Query("api_key") key: String?,
            @Query("language") language: String?
    ): Call<Details>
}