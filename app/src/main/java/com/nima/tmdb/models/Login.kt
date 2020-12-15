package com.nima.tmdb.models

data class Login(
    val username : String,
    val password : String,
    val request_token : String
)