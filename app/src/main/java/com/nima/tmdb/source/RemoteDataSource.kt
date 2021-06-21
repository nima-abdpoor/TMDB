package com.nima.tmdb.source

import com.nima.tmdb.models.Details
import com.nima.tmdb.models.Example
import com.nima.tmdb.models.account.lists.CreatedLists
import com.nima.tmdb.models.login.*
import com.nima.tmdb.models.login.account.Account
import com.nima.tmdb.models.movie.popular.PopularInfoModel
import com.nima.tmdb.models.requests.FavoriteBody
import com.nima.tmdb.models.requests.WatchlistBody
import com.nima.tmdb.models.responses.FavoriteResponse
import com.nima.tmdb.models.trend.TrendInfoModel
import com.nima.tmdb.requests.TheMovieDataBaseAPI
import com.nima.tmdb.requests.wrapper.ApiWrapper
import com.nima.tmdb.requests.wrapper.SafeApi
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api : TheMovieDataBaseAPI
) :SafeApi(){
    suspend fun searchMovieAPI(key : String ,language: String?, query: String, page: Int,include_adult : Boolean): ApiWrapper<Example> =safeApi { api.searchMovieList(key,language,query,page,include_adult)}
    suspend fun getMovieDetail(movieId : Int, apiKey: String, language: String?): ApiWrapper<Details> =safeApi { api.getMovieDetails(movieId,apiKey,language)}
    suspend fun getToken(apiKey: String): ApiWrapper<Token> =safeApi { api.getNewToken(apiKey)}
    suspend fun login(loginInfo : LoginInfo , apiKey: String): ApiWrapper<LoginResponse> =safeApi { api.login(loginInfo,apiKey)}
    suspend fun getSessionId(requestToken : RequestToken , apiKey: String): ApiWrapper<Session> =safeApi { api.getSessionId(requestToken,apiKey)}
    suspend fun getPopular(apiKey: String,language: String,page: Int,region : String): ApiWrapper<PopularInfoModel> =safeApi { api.getPopular(apiKey,language,page , region)}
    suspend fun getTrending(mediaType : String , timeWindow : String , apiKey: String): ApiWrapper<TrendInfoModel> =safeApi { api.getTrending(mediaType,timeWindow,apiKey)}
    suspend fun getAccount(apiKey: String,sessionId : String): ApiWrapper<Account> =safeApi { api.getAccountDetails(apiKey,sessionId)}
    suspend fun markAsFavorite(favoriteBody: FavoriteBody,accountId:Int,apiKey: String,sessionId:String) : ApiWrapper<FavoriteResponse> =safeApi { api.addToFavorite(favoriteBody,accountId,apiKey,sessionId)}
    suspend fun addToWatchlist(watchlistBody: WatchlistBody,accountId:Int,apiKey: String,sessionId:String) : ApiWrapper<FavoriteResponse> =safeApi { api.addToWatchlist(watchlistBody,accountId,apiKey,sessionId)}
    suspend fun getCreatedLists(accountId:String,apiKey: String,sessionId:String,language: String?,page: Int?) : ApiWrapper<CreatedLists> =safeApi { api.getCreatedLists(accountId,sessionId,apiKey,language,page)}
}