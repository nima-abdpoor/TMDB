package com.nima.tmdb.repositories

import com.nima.tmdb.business.domain.model.account.lists.CreatedLists
import com.nima.tmdb.requests.wrapper.ApiWrapper
import com.nima.tmdb.source.RemoteDataSource
import javax.inject.Inject

class CreatedListRepository @Inject constructor(
    private val remote: RemoteDataSource
) {
    suspend fun getCreatedLists(
        accountId: String,
        apiKey: String,
        sessionId: String,
        language: String?,
        page: Int?
    ): ApiWrapper<CreatedLists> =
        remote.getCreatedLists(accountId, sessionId, apiKey, language, page)
}