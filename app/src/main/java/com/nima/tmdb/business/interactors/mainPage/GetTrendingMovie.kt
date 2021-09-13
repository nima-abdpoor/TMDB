package com.nima.tmdb.business.interactors.mainPage

import com.nima.tmdb.business.data.cache.abstraction.CacheDataSource
import com.nima.tmdb.business.data.network.abstraction.RemoteDataSource
import com.nima.tmdb.business.domain.model.trend.TrendInfoModel
import com.nima.tmdb.business.data.network.requests.wrapper.ApiWrapper

class GetTrendingMovie(
    private val cache: CacheDataSource,
    private val remote: RemoteDataSource
) {
    suspend fun getTrendingMovies(
        mediaType: String,
        timeWindow: String,
        apiKey: String
    ): ApiWrapper<TrendInfoModel> {
        return remote.getTrending(mediaType, timeWindow, apiKey)
    }
}