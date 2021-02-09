package com.nima.tmdb.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MyDao {
    @Query("SELECT * FROM account")
    fun getAccount(): Account

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg account: Account)
}