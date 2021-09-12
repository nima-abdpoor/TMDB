package com.nima.tmdb.di

import com.nima.tmdb.business.data.cache.abstraction.CacheDataSource
import com.nima.tmdb.business.data.cache.implementation.CacheDataSourceImpl
import com.nima.tmdb.business.data.network.abstraction.RemoteDataSource
import com.nima.tmdb.business.data.network.implementation.RemoteDataSourceImp
import com.nima.tmdb.framewrok.datasource.cache.abstraction.DaoService
import com.nima.tmdb.framewrok.datasource.cache.implementation.DaoServiceImpl
import com.nima.tmdb.framewrok.datasource.network.abstraction.RemoteService
import com.nima.tmdb.framewrok.datasource.network.implementation.RemoteServiceImpl
import com.nima.tmdb.requests.TheMovieDataBaseAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataSourceModule {
    @Singleton
    @Provides
    fun provideRemoteService(api: TheMovieDataBaseAPI): RemoteService {
        return RemoteServiceImpl(api)
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(remote: RemoteService): RemoteDataSource {
        return RemoteDataSourceImp(remote)
    }

    @Singleton
    @Provides
    fun provideDaoService(): DaoService {
        return DaoServiceImpl()
    }

    @Singleton
    @Provides
    fun provideCacheDataSource(cacheService: DaoService): CacheDataSource {
        return CacheDataSourceImpl(cacheService)
    }
}