package com.nima.tmdb.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Account constructor(
    @PrimaryKey
    val id: Int,
    val hash: String,
    val iso6391: String,
    val iso31661: String,
    val name: String,
    val includeAdult: Boolean,
    val username: String
)