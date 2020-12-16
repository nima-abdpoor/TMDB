package com.nima.tmdb.login.state

import com.nima.tmdb.models.login.account.Account

sealed class LoginStateEvent {
    data class RequestTokenFailure(val statusCode : Int , val statusMessage : String) : LoginStateEvent()
    data class AccountDetailsFailed(val statusCode : Int , val statusMessage : String) : LoginStateEvent()
    data class TimeOutError(val message : String) : LoginStateEvent()
    data class LoginFailed(val message: String) : LoginStateEvent()
    data class SessionFailed(val message: String) : LoginStateEvent()
    data class Success(val account: Account) : LoginStateEvent()
}