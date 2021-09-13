package com.nima.tmdb.business.interactors.mainPage

import com.nima.tmdb.business.data.cache.abstraction.CacheDataSource
import com.nima.tmdb.business.data.network.abstraction.RemoteDataSource
import com.nima.tmdb.business.domain.model.requests.FavoriteBody
import com.nima.tmdb.business.domain.model.responses.FavoriteResponse
import com.nima.tmdb.business.data.network.requests.wrapper.ApiWrapper

class MarkAsFavorite(
    private val cache: CacheDataSource,
    private val remote: RemoteDataSource
) {
    suspend fun markAsFavorite(
        favoriteBody: FavoriteBody,
        accountId: Int,
        apiKey: String,
        sessionId: String
    ): ApiWrapper<FavoriteResponse> {
        return remote.markAsFavorite(favoriteBody, accountId, apiKey, sessionId)
    }
}