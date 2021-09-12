package com.nima.tmdb.business.interactors.splash

import com.nima.tmdb.business.data.cache.abstraction.CacheDataSource
import com.nima.tmdb.business.data.network.abstraction.RemoteDataSource

class GetToken(
    private val cache: CacheDataSource,
    private val remote: RemoteDataSource
) {
    suspend fun getToken(apiKey: String) {
        remote.getToken(apiKey)
    }
}