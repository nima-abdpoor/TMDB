package com.nima.tmdb.business.domain.model.login

data class LoginInfo(
    val username : String = "",
    val password : String = "",
    val request_token : String
)