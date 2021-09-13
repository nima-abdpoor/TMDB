package com.nima.tmdb.business.interactors.mainPage

import com.nima.tmdb.business.data.cache.abstraction.CacheDataSource
import com.nima.tmdb.business.data.network.abstraction.RemoteDataSource
import com.nima.tmdb.business.domain.model.login.account.Account
import com.nima.tmdb.business.data.network.requests.wrapper.ApiWrapper

class GetAccount(
    private val cache: CacheDataSource,
    private val remote: RemoteDataSource
) {
    suspend fun getAccount(apiKey: String,sessionId : String): ApiWrapper<Account>{
        return remote.getAccount(apiKey, sessionId)
    }
}