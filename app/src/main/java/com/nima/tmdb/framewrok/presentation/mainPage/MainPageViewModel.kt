package com.nima.tmdb.framewrok.presentation.mainPage

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nima.tmdb.business.domain.model.login.account.Account
import com.nima.tmdb.business.domain.model.movie.popular.PopularInfoModel
import com.nima.tmdb.business.domain.model.requests.FavoriteBody
import com.nima.tmdb.business.domain.model.requests.WatchlistBody
import com.nima.tmdb.business.domain.model.responses.FavoriteResponse
import com.nima.tmdb.business.domain.model.trend.TrendInfoModel
import com.nima.tmdb.business.interactors.mainPage.MainPageInteractors
import com.nima.tmdb.business.data.network.requests.wrapper.ApiWrapper
import kotlinx.coroutines.launch

class MainPageViewModel @ViewModelInject constructor(
    private val repository: MainPageInteractors
) : ViewModel() {

    private val _popularMovies = MutableLiveData<ApiWrapper<PopularInfoModel>>()
    val popularMovies: LiveData<ApiWrapper<PopularInfoModel>>
        get() = _popularMovies

    private val _trendingMovies = MutableLiveData<ApiWrapper<TrendInfoModel>>()
    val trendingMovies: LiveData<ApiWrapper<TrendInfoModel>>
        get() = _trendingMovies

    private val _account = MutableLiveData<ApiWrapper<Account>>()
    val accountDetail: LiveData<ApiWrapper<Account>>
        get() = _account

    private val _favorite = MutableLiveData<ApiWrapper<FavoriteResponse>>()
    val favoriteResponse: LiveData<ApiWrapper<FavoriteResponse>>
        get() = _favorite

    private val _watchlist = MutableLiveData<ApiWrapper<FavoriteResponse>>()
    val watchlistResponse: LiveData<ApiWrapper<FavoriteResponse>>
        get() = _watchlist


    fun getPopularMovies(apiKey: String, language: String, page: Int, region: String) =
        viewModelScope.launch {
            _popularMovies.value =
                repository.getPopularMovie.getPopularMovies(apiKey, language, page, region)
        }

    fun getTrendingMovies(mediaType: String, timeWindow: String, apiKey: String) =
        viewModelScope.launch {
            _trendingMovies.value =
                repository.getTrendingMovie.getTrendingMovies(mediaType, timeWindow, apiKey)
        }

    fun getAccount(apiKey: String, sessionId: String) =
        viewModelScope.launch {
            _account.value = repository.getAccount.getAccount(apiKey, sessionId)
        }

    fun markAsFavorite(
        favoriteBody: FavoriteBody,
        accountId: Int,
        apiKey: String,
        sessionId: String
    ) =
        viewModelScope.launch {
            _favorite.value =
                repository.markAsFavorite.markAsFavorite(favoriteBody, accountId, apiKey, sessionId)
        }

    fun addToWatchlist(
        watchlistBody: WatchlistBody,
        accountId: Int,
        apiKey: String,
        sessionId: String
    ) =
        viewModelScope.launch {
            _watchlist.value = repository.addToWatchList.addToWatchlist(
                watchlistBody,
                accountId,
                apiKey,
                sessionId
            )
        }
}