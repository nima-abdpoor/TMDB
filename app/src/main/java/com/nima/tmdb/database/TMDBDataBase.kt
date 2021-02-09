package com.nima.tmdb.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        Account::class
               ]
    , version = 1
)
abstract class TMDBDatabase : RoomDatabase(){
    abstract fun myDao() : MyDao
}