package com.nima.tmdb.business.data.network.implementation

import com.nima.tmdb.business.data.network.abstraction.RemoteDataSource
import com.nima.tmdb.business.domain.model.Details
import com.nima.tmdb.business.domain.model.Example
import com.nima.tmdb.business.domain.model.account.lists.CreatedLists
import com.nima.tmdb.business.domain.model.login.*
import com.nima.tmdb.business.domain.model.login.account.Account
import com.nima.tmdb.business.domain.model.movie.popular.PopularInfoModel
import com.nima.tmdb.business.domain.model.requests.FavoriteBody
import com.nima.tmdb.business.domain.model.requests.WatchlistBody
import com.nima.tmdb.business.domain.model.responses.FavoriteResponse
import com.nima.tmdb.business.domain.model.trend.TrendInfoModel
import com.nima.tmdb.framewrok.datasource.network.abstraction.RemoteService
import com.nima.tmdb.business.data.network.requests.wrapper.ApiWrapper

class RemoteDataSourceImp(
    private val remoteService: RemoteService
) : RemoteDataSource {
    override suspend fun searchMovieAPI(
        key: String,
        language: String?,
        query: String,
        page: Int,
        include_adult: Boolean
    ): ApiWrapper<Example> =
        remoteService.searchMovieAPI(key, language, query, page, include_adult)

    override suspend fun getMovieDetail(
        movieId: Int,
        apiKey: String,
        language: String?
    ): ApiWrapper<Details> = remoteService.getMovieDetail(movieId, apiKey, language)

    override suspend fun getToken(apiKey: String): ApiWrapper<Token> =
        remoteService.getToken(apiKey)

    override suspend fun login(loginInfo: LoginInfo, apiKey: String): ApiWrapper<LoginResponse> =
        remoteService.login(loginInfo, apiKey)

    override suspend fun getSessionId(
        requestToken: RequestToken,
        apiKey: String
    ): ApiWrapper<Session> = remoteService.getSessionId(requestToken, apiKey)

    override suspend fun getPopular(
        apiKey: String,
        language: String,
        page: Int,
        region: String
    ): ApiWrapper<PopularInfoModel> = remoteService.getPopular(apiKey, language, page, region)

    override suspend fun getTrending(
        mediaType: String,
        timeWindow: String,
        apiKey: String
    ): ApiWrapper<TrendInfoModel> = remoteService.getTrending(mediaType, timeWindow, apiKey)

    override suspend fun getAccount(apiKey: String, sessionId: String): ApiWrapper<Account> =
        remoteService.getAccount(apiKey, sessionId)

    override suspend fun markAsFavorite(
        favoriteBody: FavoriteBody,
        accountId: Int,
        apiKey: String,
        sessionId: String
    ): ApiWrapper<FavoriteResponse> =
        remoteService.markAsFavorite(favoriteBody, accountId, apiKey, sessionId)

    override suspend fun addToWatchlist(
        watchlistBody: WatchlistBody,
        accountId: Int,
        apiKey: String,
        sessionId: String
    ): ApiWrapper<FavoriteResponse> =
        remoteService.addToWatchlist(watchlistBody, accountId, apiKey, sessionId)

    override suspend fun getCreatedLists(
        accountId: String,
        apiKey: String,
        sessionId: String,
        language: String?,
        page: Int?
    ): ApiWrapper<CreatedLists> =
        remoteService.getCreatedLists(accountId, sessionId, apiKey, language, page)
}