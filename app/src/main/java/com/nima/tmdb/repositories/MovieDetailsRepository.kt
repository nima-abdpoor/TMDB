package com.nima.tmdb.repositories

import com.nima.tmdb.models.Details
import com.nima.tmdb.requests.wrapper.ApiWrapper
import com.nima.tmdb.requests.wrapper.SafeApi
import com.nima.tmdb.source.RemoteDataSource
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(
    private val remote: RemoteDataSource
) : SafeApi() {
    suspend fun searchMovieAPI(movieId: Int, query: String, language: String): ApiWrapper<Details> = remote.getMovieDetail(movieId,query,language)
}