package com.nima.tmdb.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nima.tmdb.business.data.cache.abstraction.CacheDataSource
import com.nima.tmdb.business.data.cache.implementation.CacheDataSourceImpl
import com.nima.tmdb.business.data.network.abstraction.RemoteDataSource
import com.nima.tmdb.business.data.network.implementation.RemoteDataSourceImp
import com.nima.tmdb.framewrok.datasource.cache.abstraction.DaoService
import com.nima.tmdb.framewrok.datasource.cache.implementation.DaoServiceImpl
import com.nima.tmdb.framewrok.datasource.network.abstraction.RemoteService
import com.nima.tmdb.framewrok.datasource.network.implementation.RemoteServiceImpl
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
object ApiModule {
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
            .connectTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(connectivity)
            .build()
    }

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            //   .serializeNulls()
            .setLenient()
            .create()
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

    @Singleton
    @Provides
    fun provideRemoteService(api: TheMovieDataBaseAPI): RemoteService {
        return RemoteServiceImpl(api)
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(remote: RemoteService): RemoteDataSource {
        return RemoteDataSourceImp(remote)
    }

    @Singleton
    @Provides
    fun provideDaoService(): DaoService {
        return DaoServiceImpl()
    }

    @Singleton
    @Provides
    fun provideCacheDataSource(cacheService: DaoService): CacheDataSource {
        return CacheDataSourceImpl(cacheService)
    }

}