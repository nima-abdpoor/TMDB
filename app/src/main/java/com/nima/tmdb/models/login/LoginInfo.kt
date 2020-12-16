package com.nima.tmdb.models.login

data class LoginInfo(
    val username : String = "",
    val password : String = "",
    val request_token : String
)