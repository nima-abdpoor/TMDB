package com.nima.tmdb.repositories

import com.nima.tmdb.business.domain.model.Example
import com.nima.tmdb.requests.wrapper.ApiWrapper
import com.nima.tmdb.business.data.network.implementation.RemoteDataSourceImp
import javax.inject.Inject

class MovieListRepository @Inject constructor(
    private val remoteImp : RemoteDataSourceImp
) {
    suspend fun searchMovieAPI(key : String ,language: String?, query: String, page: Int,include_adult : Boolean):ApiWrapper<Example> = remoteImp.searchMovieAPI(key,language,query,page,include_adult)
}