package com.nima.tmdb.business.interactors.list

import com.nima.tmdb.business.data.cache.abstraction.CacheDataSource
import com.nima.tmdb.business.data.network.abstraction.RemoteDataSource
import com.nima.tmdb.business.domain.model.Example
import com.nima.tmdb.requests.wrapper.ApiWrapper

class SearchMovie(
    private val cache: CacheDataSource,
    private val remote: RemoteDataSource
) {
    suspend fun searchMovieAPI(
        key: String,
        language: String?,
        query: String,
        page: Int,
        include_adult: Boolean
    ): ApiWrapper<Example> {
        return remote.searchMovieAPI(key, language, query, page, include_adult)
    }
}