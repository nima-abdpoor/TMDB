package com.nima.tmdb.business.interactors.mainPage

import com.nima.tmdb.business.data.cache.abstraction.CacheDataSource
import com.nima.tmdb.business.data.network.abstraction.RemoteDataSource
import com.nima.tmdb.business.domain.model.requests.WatchlistBody
import com.nima.tmdb.business.domain.model.responses.FavoriteResponse
import com.nima.tmdb.requests.wrapper.ApiWrapper

class AddToWatchList(
    private val cache: CacheDataSource,
    private val remote: RemoteDataSource
) {
    suspend fun addToWatchlist(
        watchlistBody: WatchlistBody,
        accountId: Int,
        apiKey: String,
        sessionId: String
    ): ApiWrapper<FavoriteResponse> {
        return remote.addToWatchlist(watchlistBody, accountId, apiKey, sessionId)
    }
}