package com.nima.tmdb.business.interactors.createdList

import com.nima.tmdb.business.data.cache.abstraction.CacheDataSource
import com.nima.tmdb.business.data.network.abstraction.RemoteDataSource
import com.nima.tmdb.business.domain.model.account.lists.CreatedLists
import com.nima.tmdb.business.data.network.requests.wrapper.ApiWrapper

class GetCreatedList(
    val cache: CacheDataSource,
    private val remote: RemoteDataSource
) {
    suspend fun getCreatedLists(
        accountId: String,
        apiKey: String,
        sessionId: String,
        language: String?,
        page: Int?
    ): ApiWrapper<CreatedLists> {
        return remote.getCreatedLists(accountId, apiKey, sessionId, language, page)
    }
}