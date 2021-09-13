package com.nima.tmdb.di

import android.content.Context
import androidx.room.Room
import com.nima.tmdb.framewrok.datasource.cache.database.MyDao
import com.nima.tmdb.framewrok.datasource.cache.database.TMDBDatabase
import com.nima.tmdb.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): TMDBDatabase {
        return Room.databaseBuilder(
            context, TMDBDatabase::class.java, DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideDao(dataBase: TMDBDatabase): MyDao {
        return dataBase.myDao()
    }
}