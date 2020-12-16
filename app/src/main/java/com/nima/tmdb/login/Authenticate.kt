package com.nima.tmdb.login

import android.util.Log
import com.nima.tmdb.login.state.LoginStateEvent
import com.nima.tmdb.login.state.TAG
import com.nima.tmdb.models.login.LoginInfo
import com.nima.tmdb.models.login.RequestToken
import com.nima.tmdb.requests.ServiceGenerator
import com.nima.tmdb.utils.Constants
import com.nima.tmdb.utils.Constants.API_KEY
import com.nima.tmdb.utils.Constants.TIME_OUT_SHORT
import kotlinx.coroutines.withTimeoutOrNull
import java.lang.Exception

class Authenticate {

    suspend fun requestToken(): LoginStateEvent {
        return withTimeoutOrNull(TIME_OUT_SHORT) {
            val token = ServiceGenerator.apiService().getNewToken(API_KEY)
            Log.d("TAG", "authentication: ${token.requestToken}")
            token
        }?.let { token ->
            return if (token.success) {
                Log.d(TAG, "requestToken: ${token.requestToken}")
                login(token.requestToken!!)
            } else
                LoginStateEvent.RequestTokenFailure(
                    token.statusCode!!,
                    token.statusMessage!!
                )
        } ?: let {
            return LoginStateEvent.TimeOutError("Time Out!!")
        }
    }
}

suspend fun login(requestToken: String): LoginStateEvent {
    //val login = LoginInfo("nimaabdpoot", "upf5YwB6@CXYiER", requestToken)
    val login = LoginInfo("", "", requestToken)
    try {
        return withTimeoutOrNull(TIME_OUT_SHORT) {
            ServiceGenerator.apiService().login(login, Constants.API_KEY)
        }?.let { loginResponse ->
            if (loginResponse.success)
                getSessionId(loginResponse.requestToken!!)
            else
                LoginStateEvent.LoginFailed(code = loginResponse.statusCode!!,loginResponse.statusMessage!!)
        } ?: LoginStateEvent.TimeOutError("Time Out!!")
    }
    catch (e : Exception){
        Log.d(TAG, "login: ${e.message}")
        if (e.message.equals("HTTP 401")){
            LoginStateEvent.LoginFailed(code = 401 , "Invalid username or password")
        }
        else if (e.message.equals("HTTP 401")){
            LoginStateEvent.LoginFailed(code = 400 , "You must provide a username and password.")
        }
        return LoginStateEvent.LoginFailed(404, "Failed To Login")
    }
}


private suspend fun getSessionId(_requestToken: String): LoginStateEvent {
    val requestToken = RequestToken(_requestToken)
    return withTimeoutOrNull(TIME_OUT_SHORT) {
        ServiceGenerator.apiService().getSessionId(requestToken, API_KEY)
    }?.let { session ->
        if (session.success) {
            Log.d(TAG, "getSessionId: ${session.sessionId}")
            getAccountDetails(session.sessionId!!)
        } else
            return LoginStateEvent.SessionFailed("${session.statusMessage} code : ${session.statusCode}")
    } ?: LoginStateEvent.TimeOutError("Time Out!!")
}

suspend fun getAccountDetails(sessionId: String): LoginStateEvent {
    return withTimeoutOrNull(TIME_OUT_SHORT) {
        ServiceGenerator.apiService().getAccountDetails(API_KEY, sessionId)
    }?.let { account ->
        account.statusCode?.let {
            return LoginStateEvent.AccountDetailsFailed(
                account.statusCode!!,
                account.statusMessage!!
            )
        } ?: return LoginStateEvent.Success(account)
    } ?: LoginStateEvent.TimeOutError("Time Out!!")
}
