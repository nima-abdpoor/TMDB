package com.nima.tmdb.framewrok.datasource.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nima.tmdb.framewrok.datasource.cache.entities.Account
import com.nima.tmdb.framewrok.datasource.cache.entities.UserInfo

@Database(
    entities = [
        Account::class,
        UserInfo::class
    ], version = 1
)
abstract class TMDBDatabase : RoomDatabase() {
    abstract fun myDao(): MyDao
}