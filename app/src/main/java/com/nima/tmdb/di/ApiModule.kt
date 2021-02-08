package com.nima.tmdb.di

import android.content.Context
import com.google.gson.Gson
import com.nima.tmdb.requests.TheMovieDataBaseAPI
import com.nima.tmdb.requests.wrapper.Connectivity
import com.nima.tmdb.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class ApiModule {
    @Provides
    fun provideConnectivity(@ApplicationContext context: Context): Connectivity {
        return Connectivity(context)
    }

    @Singleton
    @Provides
    fun provideOkHttp(connectivity: Connectivity): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(8000, TimeUnit.SECONDS)
            .writeTimeout(8000, TimeUnit.SECONDS)
            .connectTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(connectivity)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit.Builder): TheMovieDataBaseAPI {
        return retrofit.build().create(TheMovieDataBaseAPI::class.java)
    }

}