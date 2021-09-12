package com.nima.tmdb.repositories

import com.nima.tmdb.business.domain.model.Details
import com.nima.tmdb.requests.wrapper.ApiWrapper
import com.nima.tmdb.requests.wrapper.SafeApi
import com.nima.tmdb.business.data.network.implementation.RemoteDataSourceImp
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(
    private val remoteImp: RemoteDataSourceImp
) : SafeApi() {
    suspend fun searchMovieAPI(movieId: Int, apiKey: String, language: String): ApiWrapper<Details> = remoteImp.getMovieDetail(movieId,apiKey,language)
}