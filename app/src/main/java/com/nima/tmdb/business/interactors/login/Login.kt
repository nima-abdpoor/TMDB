package com.nima.tmdb.business.interactors.login

import com.nima.tmdb.business.data.cache.abstraction.CacheDataSource
import com.nima.tmdb.business.data.network.abstraction.RemoteDataSource
import com.nima.tmdb.business.domain.model.login.LoginInfo
import com.nima.tmdb.business.domain.model.login.LoginResponse
import com.nima.tmdb.business.data.network.requests.wrapper.ApiWrapper

class Login(
    private val cache: CacheDataSource,
    private val remote: RemoteDataSource
) {
    suspend fun login(loginInfo: LoginInfo, apiKey: String): ApiWrapper<LoginResponse> {
        return remote.login(loginInfo, apiKey)
    }
}