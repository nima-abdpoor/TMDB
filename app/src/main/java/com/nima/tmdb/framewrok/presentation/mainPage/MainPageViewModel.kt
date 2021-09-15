package com.nima.tmdb.framewrok.presentation.mainPage

import androidx.hilt.lifecycle.ViewModelInject
import com.nima.tmdb.business.domain.model.requests.FavoriteBody
import com.nima.tmdb.business.domain.model.requests.WatchlistBody
import com.nima.tmdb.business.interactors.mainPage.MainPageInteractors
import com.nima.tmdb.framewrok.presentation.common.BaseViewModel

class MainPageViewModel @ViewModelInject constructor(
    private val repository: MainPageInteractors
) : BaseViewModel() {

    fun getPopularMovies(apiKey: String, language: String, page: Int, region: String) =
        execute { repository.getPopularMovie.getPopularMovies(apiKey, language, page, region) }

    fun getTrendingMovies(mediaType: String, timeWindow: String, apiKey: String) =
        execute { repository.getTrendingMovie.getTrendingMovies(mediaType, timeWindow, apiKey) }

    fun getAccount(apiKey: String, sessionId: String) =
        execute { repository.getAccount.getAccount(apiKey, sessionId) }

    fun markAsFavorite(
        favoriteBody: FavoriteBody,
        accountId: Int,
        apiKey: String,
        sessionId: String
    ) =
        execute {
            repository.markAsFavorite.markAsFavorite(
                favoriteBody,
                accountId,
                apiKey,
                sessionId
            )
        }

    fun addToWatchlist(
        watchlistBody: WatchlistBody,
        accountId: Int,
        apiKey: String,
        sessionId: String
    ) =
        execute {
            repository.addToWatchList.addToWatchlist(
                watchlistBody,
                accountId,
                apiKey,
                sessionId
            )
        }
}