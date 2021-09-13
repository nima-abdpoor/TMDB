package com.nima.tmdb.di

import com.nima.tmdb.business.data.cache.abstraction.CacheDataSource
import com.nima.tmdb.business.data.cache.implementation.CacheDataSourceImpl
import com.nima.tmdb.business.data.network.abstraction.RemoteDataSource
import com.nima.tmdb.business.data.network.implementation.RemoteDataSourceImp
import com.nima.tmdb.business.interactors.createdList.CreatedListInteractors
import com.nima.tmdb.business.interactors.createdList.GetCreatedList
import com.nima.tmdb.business.interactors.details.DetailsInteractors
import com.nima.tmdb.business.interactors.details.GetMovieById
import com.nima.tmdb.business.interactors.list.ListInteractors
import com.nima.tmdb.business.interactors.list.SearchMovie
import com.nima.tmdb.business.interactors.login.GetSessionId
import com.nima.tmdb.business.interactors.login.Login
import com.nima.tmdb.business.interactors.login.LoginInteractors
import com.nima.tmdb.business.interactors.mainPage.*
import com.nima.tmdb.business.interactors.splash.GetToken
import com.nima.tmdb.business.interactors.splash.SplashInteractors
import com.nima.tmdb.framewrok.datasource.cache.abstraction.DaoService
import com.nima.tmdb.framewrok.datasource.cache.implementation.DaoServiceImpl
import com.nima.tmdb.framewrok.datasource.network.abstraction.RemoteService
import com.nima.tmdb.framewrok.datasource.network.implementation.RemoteServiceImpl
import com.nima.tmdb.business.data.network.requests.TheMovieDataBaseAPI
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

    @Singleton
    @Provides
    fun provideLoginInteractors(
        cacheDataSource: CacheDataSource,
        remoteDataSource: RemoteDataSource
    ): LoginInteractors {
        return LoginInteractors(
            GetSessionId(cacheDataSource, remoteDataSource),
            Login(cacheDataSource, remoteDataSource)
        )
    }

    @Singleton
    @Provides
    fun provideMainPageInteractors(
        cacheDataSource: CacheDataSource,
        remoteDataSource: RemoteDataSource
    ): MainPageInteractors {
        return MainPageInteractors(
            AddToWatchList(cacheDataSource, remoteDataSource),
            GetAccount(cacheDataSource, remoteDataSource),
            GetPopularMovie(cacheDataSource, remoteDataSource),
            GetTrendingMovie(cacheDataSource, remoteDataSource),
            MarkAsFavorite(cacheDataSource, remoteDataSource)
        )
    }

    @Singleton
    @Provides
    fun provideSplashInteractors(
        cacheDataSource: CacheDataSource,
        remoteDataSource: RemoteDataSource
    ): SplashInteractors {
        return SplashInteractors(
            GetSessionId(cacheDataSource, remoteDataSource),
            Login(cacheDataSource, remoteDataSource),
            GetToken(cacheDataSource, remoteDataSource)
        )
    }

    @Singleton
    @Provides
    fun provideDetailsInteractors(
        cacheDataSource: CacheDataSource,
        remoteDataSource: RemoteDataSource
    ): DetailsInteractors {
        return DetailsInteractors(GetMovieById(cacheDataSource, remoteDataSource))
    }

    @Singleton
    @Provides
    fun provideListInteractors(
        cacheDataSource: CacheDataSource,
        remoteDataSource: RemoteDataSource
    ): ListInteractors {
        return ListInteractors(SearchMovie(cacheDataSource, remoteDataSource))
    }

    @Singleton
    @Provides
    fun provideCreatedListInteractors(
        cacheDataSource: CacheDataSource,
        remoteDataSource: RemoteDataSource
    ): CreatedListInteractors {
        return CreatedListInteractors(GetCreatedList(cacheDataSource, remoteDataSource))
    }
}