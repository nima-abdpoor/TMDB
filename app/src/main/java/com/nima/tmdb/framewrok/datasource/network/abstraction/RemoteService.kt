package com.nima.tmdb.framewrok.datasource.network.abstraction

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
import com.nima.tmdb.business.data.network.requests.wrapper.ApiWrapper

interface RemoteService {
    suspend fun searchMovieAPI(key : String ,language: String?, query: String, page: Int,include_adult : Boolean): ApiWrapper<Example>

    suspend fun getMovieDetail(movieId : Int, apiKey: String, language: String?): ApiWrapper<Details>

    suspend fun getToken(apiKey: String): ApiWrapper<Token>

    suspend fun login(loginInfo : LoginInfo, apiKey: String): ApiWrapper<LoginResponse>

    suspend fun getSessionId(requestToken : RequestToken, apiKey: String): ApiWrapper<Session>

    suspend fun getPopular(apiKey: String,language: String,page: Int,region : String): ApiWrapper<PopularInfoModel>

    suspend fun getTrending(mediaType : String , timeWindow : String , apiKey: String): ApiWrapper<TrendInfoModel>

    suspend fun getAccount(apiKey: String,sessionId : String): ApiWrapper<Account>

    suspend fun markAsFavorite(favoriteBody: FavoriteBody, accountId:Int, apiKey: String, sessionId:String): ApiWrapper<FavoriteResponse>

    suspend fun addToWatchlist(watchlistBody: WatchlistBody, accountId:Int, apiKey: String, sessionId:String): ApiWrapper<FavoriteResponse>

    suspend fun getCreatedLists(accountId:String,apiKey: String,sessionId:String,language: String?,page: Int?): ApiWrapper<CreatedLists>
}