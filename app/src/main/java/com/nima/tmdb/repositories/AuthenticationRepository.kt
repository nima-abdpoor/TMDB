package com.nima.tmdb.repositories

import com.nima.tmdb.models.login.*
import com.nima.tmdb.requests.wrapper.ApiWrapper
import com.nima.tmdb.requests.wrapper.SafeApi
import com.nima.tmdb.source.RemoteDataSource
import javax.inject.Inject


class AuthenticationRepository @Inject constructor(
    private val remote: RemoteDataSource
) : SafeApi(){
    suspend fun requestToken(apiKey: String): ApiWrapper<Token> = remote.getToken(apiKey)
    suspend fun login(loginInfo: LoginInfo,apiKey: String): ApiWrapper<LoginResponse> = remote.login(loginInfo,apiKey)
    suspend fun getSessionId(requestToken : RequestToken, apiKey: String): ApiWrapper<Session> = remote.getSessionId(requestToken,apiKey)
}