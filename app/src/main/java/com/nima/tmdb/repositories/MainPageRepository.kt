package com.nima.tmdb.repositories

import com.nima.tmdb.models.login.account.Account
import com.nima.tmdb.models.movie.popular.PopularInfoModel
import com.nima.tmdb.models.requests.FavoriteBody
import com.nima.tmdb.models.responses.FavoriteResponse
import com.nima.tmdb.models.trend.TrendInfoModel
import com.nima.tmdb.requests.wrapper.ApiWrapper
import com.nima.tmdb.source.RemoteDataSource
import javax.inject.Inject

class MainPageRepository @Inject constructor(
 private val remote : RemoteDataSource
) {
    suspend fun getPopularMovies(apiKey: String,language: String,page: Int,region : String): ApiWrapper<PopularInfoModel> = remote.getPopular(apiKey,language,page, region)
    suspend fun getTrending(mediaType : String , timeWindow : String , apiKey: String): ApiWrapper<TrendInfoModel> = remote.getTrending(mediaType,timeWindow,apiKey)
    suspend fun getAccount(apiKey: String,sessionId:String): ApiWrapper<Account> = remote.getAccount(apiKey,sessionId)
    suspend fun markAsFavorite(favoriteBody: FavoriteBody, accountId:Int, apiKey: String, sessionId:String): ApiWrapper<FavoriteResponse> = remote.markAsFavorite(favoriteBody,accountId,apiKey,sessionId)
}