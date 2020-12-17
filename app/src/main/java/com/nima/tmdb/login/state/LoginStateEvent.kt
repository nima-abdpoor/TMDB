package com.nima.tmdb.login.state

import android.util.Log
import com.nima.tmdb.models.login.account.Account
const val TAG : String = "LoginStateEvent"

sealed class LoginStateEvent : State(){
    data class AccountDetailsFailed(val statusCode : Int , val statusMessage : String) : LoginStateEvent()
    data class TimeOutError(val message : String) : LoginStateEvent()
    data class LoginFailed(val code : Int , val message: String) : LoginStateEvent()
    data class SessionFailed(val message: String) : LoginStateEvent()
    data class Success(val account: Account) : LoginStateEvent()
}

fun log(statusCode: Int?, statusMessage: String , methodName : String? = null){
    Log.d(TAG, "log: $statusMessage with $statusCode in $methodName")
}