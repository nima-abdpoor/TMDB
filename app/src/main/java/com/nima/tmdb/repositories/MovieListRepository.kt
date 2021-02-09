package com.nima.tmdb.repositories

import com.nima.tmdb.models.Example
import com.nima.tmdb.requests.wrapper.ApiWrapper
import com.nima.tmdb.source.RemoteDataSource
import javax.inject.Inject

class MovieListRepository @Inject constructor(
    private val remote : RemoteDataSource
) {
    suspend fun searchMovieAPI(key : String ,language: String?, query: String, page: Int,include_adult : Boolean):ApiWrapper<Example> = remote.searchMovieAPI(key,language,query,page,include_adult)
}