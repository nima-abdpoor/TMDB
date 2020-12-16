package com.nima.tmdb.login.state

sealed class LoginStateEvent {
    data class RequestTokenFailure(val statusCode : Int , val statusMessage : String) : LoginStateEvent()
    data class TimeOutError(val message : String) : LoginStateEvent()
    data class LoginFailed(val message: String) : LoginStateEvent()
    data class SessionFailed(val message: String) : LoginStateEvent()
}