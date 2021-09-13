package com.nima.tmdb.business.interactors.mainPage

class MainPageInteractors(
    val addToWatchList: AddToWatchList,
    val getAccount: GetAccount,
    val getPopularMovie: GetPopularMovie,
    val getTrendingMovie: GetTrendingMovie,
    val markAsFavorite: MarkAsFavorite
)