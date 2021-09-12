package com.nima.tmdb.repositories

import com.nima.tmdb.business.domain.model.account.lists.CreatedLists
import com.nima.tmdb.requests.wrapper.ApiWrapper
import com.nima.tmdb.business.data.network.implementation.RemoteDataSourceImp
import javax.inject.Inject

class CreatedListRepository @Inject constructor(
    private val remoteImp: RemoteDataSourceImp
) {
    suspend fun getCreatedLists(
        accountId: String,
        apiKey: String,
        sessionId: String,
        language: String?,
        page: Int?
    ): ApiWrapper<CreatedLists> =
        remoteImp.getCreatedLists(accountId, sessionId, apiKey, language, page)
}