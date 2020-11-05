package com.nima.tmdb.requests


import com.nima.tmdb.utils.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceGenerator {
    private val retrofitBuilder: Retrofit.Builder = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        //.addCallAdapterFactory(LiveDataCallAdapterFactory())
        .baseUrl(BASE_URL)


    private val retrofit = retrofitBuilder
        .build()
        .create(TheMovieDataBaseAPI::class.java)


    fun apiService(): TheMovieDataBaseAPI {
        return retrofit
    }
}