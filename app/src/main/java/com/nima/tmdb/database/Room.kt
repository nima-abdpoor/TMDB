package com.nima.tmdb.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AccountDao {
    @Query("SELECT * FROM account")
    fun getAccount(): Account

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg account: Account)
}
@Database(entities = [Account::class] , version = 1)
abstract class TMDBDatabase : RoomDatabase(){
    abstract val accountDao : AccountDao
}

private lateinit var INSTANCE: TMDBDatabase

fun getDatabase(context: Context): TMDBDatabase {
    synchronized(TMDBDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext ,
                TMDBDatabase::class.java,
                "TMDB"
            ).build()
        }
    }
    return INSTANCE
}