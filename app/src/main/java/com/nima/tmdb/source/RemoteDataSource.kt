package com.nima.tmdb.source

import com.nima.tmdb.models.Details
import com.nima.tmdb.models.Example
import com.nima.tmdb.requests.TheMovieDataBaseAPI
import com.nima.tmdb.requests.wrapper.ApiWrapper
import com.nima.tmdb.requests.wrapper.SafeApi
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api : TheMovieDataBaseAPI
) :SafeApi(){
    suspend fun searchMovieAPI(key : String ,language: String?, query: String, page: Int,include_adult : Boolean): ApiWrapper<Example> =safeApi { api.searchMovieList(key,language,query,page,include_adult)}
    suspend fun getMovieDetail(movieId : Int, query: String,language: String?): ApiWrapper<Details> =safeApi { api.getMovieDetails(movieId,query,language)}
}