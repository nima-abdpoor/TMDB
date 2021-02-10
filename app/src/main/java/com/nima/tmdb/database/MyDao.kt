package com.nima.tmdb.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nima.tmdb.database.entities.Account
import com.nima.tmdb.database.entities.UserInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDao {

    //Account
    @Query("SELECT * FROM account")
    fun getAccount(): Account
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAccount(vararg account: Account)

    //UserInfo
    @Query("SELECT * FROM userInfo")
    fun getUserInfo() : Flow<UserInfo?>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUserInfo(vararg userInfo: UserInfo)

}