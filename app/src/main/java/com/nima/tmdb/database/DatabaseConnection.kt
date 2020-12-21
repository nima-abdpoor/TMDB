package com.nima.tmdb.database

interface DatabaseConnection<T>{
    fun save(T : T)
    fun load()
}