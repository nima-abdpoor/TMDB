package com.nima.tmdb.business.interactors.splash

import com.nima.tmdb.business.data.cache.abstraction.CacheDataSource
import com.nima.tmdb.business.data.network.abstraction.RemoteDataSource
import com.nima.tmdb.business.domain.model.login.Token
import com.nima.tmdb.requests.wrapper.ApiWrapper

class GetToken(
    private val cache: CacheDataSource,
    private val remote: RemoteDataSource
) {
    suspend fun getToken(apiKey: String): ApiWrapper<Token> {
        return remote.getToken(apiKey)
    }
}