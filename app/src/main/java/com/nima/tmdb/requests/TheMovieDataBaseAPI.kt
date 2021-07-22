package com.nima.tmdb.requests


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
import retrofit2.Response
import retrofit2.http.*

interface TheMovieDataBaseAPI {
    @GET("search/movie")
    suspend fun searchMovieList(
            @Query("api_key") key: String,
            @Query("language") language: String?,
            @Query("query") query: String?,
            @Query("page") page: Int,
            @Query("include_adult") include_adult: Boolean
    ): Response<Example>

    @GET("movie/{movieID}")
    suspend fun getMovieDetails(
            @Path("movieID") movieID: Int,
            @Query("api_key") key: String?,
            @Query("language") language: String?
    ): Response<Details>

    @GET("authentication/token/new")
    suspend fun getNewToken(
        @Query("api_key") key: String?,
    ): Response<Token>

    @POST("authentication/token/validate_with_login")
    suspend fun login(@Body loginInfo : LoginInfo, @Query("api_key") key: String?) : Response<LoginResponse>

    //https://developers.themoviedb.org/3/account/mark-as-favorite
    @POST("account/{account_id}/favorite")
    suspend fun addToFavorite(@Body favoriteBody: FavoriteBody,
                              @Path("account_id") accountId: Int,
                              @Query("api_key") key: String,
                              @Query("session_id") sessionId: String,
    ) : Response<FavoriteResponse>

    //https://developers.themoviedb.org/3/account/add-to-watchlist
    @POST("/account/{account_id}/watchlist")
    suspend fun addToWatchlist(@Body watchlistBody: WatchlistBody,
                              @Path("account_id") accountId: Int,
                              @Query("api_key") key: String,
                              @Query("session_id") sessionId: String,
    ) : Response<FavoriteResponse>


    @POST("authentication/session/new")
    suspend fun getSessionId(@Body requestToken : RequestToken, @Query("api_key") key: String?) : Response<Session>

    @GET("account")
    suspend fun getAccountDetails(
        @Query("api_key") key: String,
        @Query("session_id") sessionId: String,
    ): Response<Account>

    @GET("movie/popular")
    suspend fun getPopular(
        @Query("api_key") key: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String="",
    ) : Response<PopularInfoModel>

    @GET("trending/{media_type}/{time_window}")
    suspend fun getTrending(
        @Path("media_type") type: String,
        @Path("time_window") timeWindow: String,
        @Query("api_key") key: String
    ) : Response<TrendInfoModel>

    @GET("account/{account_id}/lists")
    suspend fun getCreatedLists(
        @Path("account_id") accountId: String,
        @Query("session_id") sessionId: String,
        @Query("api_key") key: String,
        @Query("language") language: String?,
        @Query("page") page: Int?
    ) : Response<CreatedLists>

    @GET("account/{account_id}/favorite/movies")
    suspend fun getFavorite(
        @Path("account_id") accountId: String,
        @Query("session_id") sessionId: String,
        @Query("api_key") key: String,
        @Query("language") language: String?,
        @Query("sort_by") created_at: String?,
        @Query("page") page: Int?
    ) : Response<Example>
}