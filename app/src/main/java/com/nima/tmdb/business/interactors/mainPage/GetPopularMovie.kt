package com.nima.tmdb.business.interactors.mainPage

import com.nima.tmdb.business.data.cache.abstraction.CacheDataSource
import com.nima.tmdb.business.data.network.abstraction.RemoteDataSource
import com.nima.tmdb.business.domain.model.movie.popular.PopularInfoModel
import com.nima.tmdb.requests.wrapper.ApiWrapper

class GetPopularMovie(
    private val cache: CacheDataSource,
    private val remote: RemoteDataSource
) {

    suspend fun getPopularMovies(
        apiKey: String,
        language: String,
        page: Int,
        region: String
    ): ApiWrapper<PopularInfoModel> {
        return remote.getPopular(apiKey, language, page, region)
    }
}