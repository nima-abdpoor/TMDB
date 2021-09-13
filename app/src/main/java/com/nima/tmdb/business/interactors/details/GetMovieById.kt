package com.nima.tmdb.business.interactors.details

import com.nima.tmdb.business.data.cache.abstraction.CacheDataSource
import com.nima.tmdb.business.data.network.abstraction.RemoteDataSource
import com.nima.tmdb.business.domain.model.Details
import com.nima.tmdb.business.data.network.requests.wrapper.ApiWrapper

class GetMovieById(
    private val cache: CacheDataSource,
    private val remote: RemoteDataSource
) {
    suspend fun searchMovieAPI(
        movieId: Int,
        apiKey: String,
        language: String
    ): ApiWrapper<Details> {
        return remote.getMovieDetail(movieId, apiKey, language)
    }
}