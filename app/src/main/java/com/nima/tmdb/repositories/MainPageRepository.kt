package com.nima.tmdb.repositories

import com.nima.tmdb.models.movie.popular.Popular
import com.nima.tmdb.models.trend.Trend
import com.nima.tmdb.requests.wrapper.ApiWrapper
import com.nima.tmdb.source.RemoteDataSource
import javax.inject.Inject

class MainPageRepository @Inject constructor(
 private val remote : RemoteDataSource
) {
    suspend fun getPopularMovies(apiKey: String,language: String,page: Int,region : String): ApiWrapper<Popular> = remote.getPopular(apiKey,language,page, region)
    suspend fun getTrending(mediaType : String , timeWindow : String , apiKey: String): ApiWrapper<Trend> = remote.getTrending(mediaType,timeWindow,apiKey)
}