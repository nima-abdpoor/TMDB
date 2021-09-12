package com.nima.tmdb.repositories

import com.nima.tmdb.business.domain.model.login.LoginInfo
import com.nima.tmdb.business.domain.model.login.LoginResponse
import com.nima.tmdb.business.domain.model.login.RequestToken
import com.nima.tmdb.business.domain.model.login.Session
import com.nima.tmdb.requests.wrapper.ApiWrapper
import com.nima.tmdb.requests.wrapper.SafeApi
import com.nima.tmdb.business.data.network.implementation.RemoteDataSourceImp
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val remoteImp: RemoteDataSourceImp
) : SafeApi() {

    suspend fun login(loginInfo : LoginInfo, apiKey: String): ApiWrapper<LoginResponse> = remoteImp.login(loginInfo,apiKey)
    suspend fun getSessionId(requestToken : RequestToken, apiKey: String): ApiWrapper<Session> = remoteImp.getSessionId(requestToken,apiKey)
}