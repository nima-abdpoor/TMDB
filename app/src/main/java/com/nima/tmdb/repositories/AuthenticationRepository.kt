package com.nima.tmdb.repositories

import com.nima.tmdb.database.MyDao
import com.nima.tmdb.business.domain.model.login.*
import com.nima.tmdb.requests.wrapper.ApiWrapper
import com.nima.tmdb.requests.wrapper.SafeApi
import com.nima.tmdb.business.data.network.implementation.RemoteDataSourceImp
import javax.inject.Inject


class AuthenticationRepository @Inject constructor(
    private val remoteImp: RemoteDataSourceImp
) : SafeApi(){
    @Inject
    lateinit var dao: MyDao

    suspend fun requestToken(apiKey: String): ApiWrapper<Token> = remoteImp.getToken(apiKey)
    suspend fun login(username: String,password :String , requestToken: String , apiKey: String): ApiWrapper<LoginResponse> {
        val loginInfo = LoginInfo(username ,password ,requestToken)
        return remoteImp.login(loginInfo,apiKey)
    }
    suspend fun getSessionId(requestToken : RequestToken, apiKey: String): ApiWrapper<Session> = remoteImp.getSessionId(requestToken,apiKey)


}