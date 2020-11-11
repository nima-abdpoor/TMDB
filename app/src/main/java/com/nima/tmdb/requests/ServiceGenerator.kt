package com.nima.tmdb.requests


import android.util.Log
import com.google.gson.GsonBuilder
import com.nima.tmdb.utils.Constants.BASE_URL
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object ServiceGenerator {

    private val okHttpClient by lazy { OkHttpClient() }

    private val retrofitBuilder: Retrofit by lazy {
        Log.e("AppClient", "Creating Retrofit Client")
        val builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))

        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client: OkHttpClient = okHttpClient.newBuilder()
            .connectTimeout(4, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .dispatcher(dispatcher)
            .build()
        builder.client(client).build()
    }

    private val retrofit = retrofitBuilder
        .create(TheMovieDataBaseAPI::class.java)


    fun apiService(): TheMovieDataBaseAPI {
        return retrofit
    }
}