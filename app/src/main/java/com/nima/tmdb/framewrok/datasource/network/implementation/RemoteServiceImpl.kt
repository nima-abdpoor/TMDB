package com.nima.tmdb.framewrok.datasource.network.implementation

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
import com.nima.tmdb.business.data.network.requests.TheMovieDataBaseAPI
import com.nima.tmdb.business.data.network.requests.wrapper.ApiWrapper
import com.nima.tmdb.business.data.network.requests.wrapper.SafeApi

class RemoteServiceImpl(
    private val api: TheMovieDataBaseAPI
) : SafeApi(), RemoteService {
    override suspend fun searchMovieAPI(
        key: String,
        language: String?,
        query: String,
        page: Int,
        include_adult: Boolean
    ): ApiWrapper<Example> =
        safeApi { api.searchMovieList(key, language, query, page, include_adult) }

    override suspend fun getMovieDetail(
        movieId: Int,
        apiKey: String,
        language: String?
    ): ApiWrapper<Details> = safeApi { api.getMovieDetails(movieId, apiKey, language) }

    override suspend fun getToken(apiKey: String): ApiWrapper<Token> =
        safeApi { api.getNewToken(apiKey) }

    override suspend fun login(loginInfo: LoginInfo, apiKey: String): ApiWrapper<LoginResponse> =
        safeApi { api.login(loginInfo, apiKey) }

    override suspend fun getSessionId(
        requestToken: RequestToken,
        apiKey: String
    ): ApiWrapper<Session> = safeApi { api.getSessionId(requestToken, apiKey) }

    override suspend fun getPopular(
        apiKey: String,
        language: String,
        page: Int,
        region: String
    ): ApiWrapper<PopularInfoModel> = safeApi { api.getPopular(apiKey, language, page, region) }

    override suspend fun getTrending(
        mediaType: String,
        timeWindow: String,
        apiKey: String
    ): ApiWrapper<TrendInfoModel> = safeApi { api.getTrending(mediaType, timeWindow, apiKey) }

    override suspend fun getAccount(apiKey: String, sessionId: String): ApiWrapper<Account> =
        safeApi { api.getAccountDetails(apiKey, sessionId) }

    override suspend fun markAsFavorite(
        favoriteBody: FavoriteBody,
        accountId: Int,
        apiKey: String,
        sessionId: String
    ): ApiWrapper<FavoriteResponse> =
        safeApi { api.addToFavorite(favoriteBody, accountId, apiKey, sessionId) }

    override suspend fun addToWatchlist(
        watchlistBody: WatchlistBody,
        accountId: Int,
        apiKey: String,
        sessionId: String
    ): ApiWrapper<FavoriteResponse> =
        safeApi { api.addToWatchlist(watchlistBody, accountId, apiKey, sessionId) }

    override suspend fun getCreatedLists(
        accountId: String,
        apiKey: String,
        sessionId: String,
        language: String?,
        page: Int?
    ): ApiWrapper<CreatedLists> =
        safeApi { api.getCreatedLists(accountId, sessionId, apiKey, language, page) }
}