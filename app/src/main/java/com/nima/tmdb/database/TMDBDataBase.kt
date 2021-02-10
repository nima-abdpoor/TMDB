package com.nima.tmdb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nima.tmdb.database.entities.Account
import com.nima.tmdb.database.entities.UserInfo

@Database(
    entities = [
        Account::class,
        UserInfo::class
    ], version = 2
)
abstract class TMDBDatabase : RoomDatabase() {
    abstract fun myDao(): MyDao
}