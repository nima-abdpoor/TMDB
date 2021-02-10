package com.nima.tmdb.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserInfo constructor(
    @PrimaryKey
    val id : Int? = 0,
    val userName : String? = "",
    val password: String? ="",
)