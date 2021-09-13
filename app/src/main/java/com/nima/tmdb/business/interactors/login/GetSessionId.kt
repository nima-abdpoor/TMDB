package com.nima.tmdb.business.interactors.login

import com.nima.tmdb.business.data.cache.abstraction.CacheDataSource
import com.nima.tmdb.business.data.network.abstraction.RemoteDataSource
import com.nima.tmdb.business.domain.model.login.RequestToken
import com.nima.tmdb.business.domain.model.login.Session
import com.nima.tmdb.business.data.network.requests.wrapper.ApiWrapper

class GetSessionId(
    private val cache: CacheDataSource,
    private val remote: RemoteDataSource
) {

    suspend fun getSessionId(requestToken: RequestToken, apiKey: String): ApiWrapper<Session> {
        return remote.getSessionId(requestToken, apiKey)
    }

}