package com.nima.tmdb.business.data.network.requests.wrapper

import okhttp3.Headers

sealed class ApiWrapper<T>(
    val data: T? = null,
    val headers: Headers? = null,
    val message: String? = null,
    val error: String? = null,
    val code: Int? = null,
    val totalError: String? = null
) {
    class Success <T> (data: T, headers: Headers, code: Int): ApiWrapper<T>(data ,headers ,null ,null ,code)
    class ApiError<T> (message: String,error: String,code: Int ,totalError: String): ApiWrapper<T>(null,null,message,error,code,totalError)
    class NetworkError<T>(message: String): ApiWrapper<T>(null,null,message)
    class UnknownError<T>(message: String): ApiWrapper<T>(null,null,message)
}