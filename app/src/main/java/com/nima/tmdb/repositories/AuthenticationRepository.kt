package com.nima.tmdb.repositories

import com.nima.tmdb.database.MyDao
import com.nima.tmdb.database.entities.UserInfo
import com.nima.tmdb.models.login.*
import com.nima.tmdb.requests.wrapper.ApiWrapper
import com.nima.tmdb.requests.wrapper.SafeApi
import com.nima.tmdb.source.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class AuthenticationRepository @Inject constructor(
    private val remote: RemoteDataSource
) : SafeApi(){
    @Inject
    lateinit var dao: MyDao

    suspend fun requestToken(apiKey: String): ApiWrapper<Token> = remote.getToken(apiKey)
    suspend fun login(userInfo: UserInfo,requestToken: String , apiKey: String): ApiWrapper<LoginResponse> {
        val loginInfo = LoginInfo(username = userInfo.userName!! ,password = userInfo.password!!,requestToken)
        return remote.login(loginInfo,apiKey)
    }
    suspend fun getSessionId(requestToken : RequestToken, apiKey: String): ApiWrapper<Session> = remote.getSessionId(requestToken,apiKey)
    fun getUserInfo() : Flow<UserInfo?> = dao.getUserInfo()

}