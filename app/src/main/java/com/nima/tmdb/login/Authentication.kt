package com.nima.tmdb.login

import android.content.Context
import android.util.Log
import com.nima.tmdb.login.state.LoginStateEvent
import com.nima.tmdb.models.login.LoginInfo
import com.nima.tmdb.models.login.RequestToken
import com.nima.tmdb.requests.ServiceGenerator
import com.nima.tmdb.utils.Constants
import kotlinx.coroutines.withTimeoutOrNull
import java.lang.Exception

class Authentication(context : Context) {
    private val userInfo = UserInfo(context)

    suspend fun login(requestToken: String): LoginStateEvent {
        val login = getLoginInfo(requestToken)
        Log.d(com.nima.tmdb.login.state.TAG, "login: ${login.username} +++${login.password}")
        try {
            return withTimeoutOrNull(Constants.TIME_OUT_SHORT) {
                ServiceGenerator.apiService().login(login, Constants.API_KEY)
            }?.let { loginResponse ->
                if (loginResponse.success)
                    getSessionId(loginResponse.requestToken!!)
                else
                    LoginStateEvent.LoginFailed(
                        code = loginResponse.statusCode!!,
                        loginResponse.statusMessage!!
                    )
            } ?: LoginStateEvent.TimeOutError("Time Out!!")
        } catch (e: Exception) {
            Log.d(com.nima.tmdb.login.state.TAG, "login: ${e.message}")
            if (e.message.equals("HTTP 401")) {
                LoginStateEvent.LoginFailed(code = 401, "Invalid username or password")
            } else if (e.message.equals("HTTP 401")) {
                LoginStateEvent.LoginFailed(code = 400, "You must provide a username and password.")
            }
            return LoginStateEvent.LoginFailed(404, "Failed To Login")
        }
    }

    private fun getLoginInfo(requestToken: String): LoginInfo {
        return userInfo.getUserInfo(requestToken)
        //return LoginInfo("nimaabdpoot", "upf5YwB6@CXYiER", requestToken)
    }


    private suspend fun getSessionId(_requestToken: String): LoginStateEvent {
        val requestToken = RequestToken(_requestToken)
        return withTimeoutOrNull(Constants.TIME_OUT_SHORT) {
            ServiceGenerator.apiService().getSessionId(requestToken, Constants.API_KEY)
        }?.let { session ->
            if (session.success) {
                Log.d(com.nima.tmdb.login.state.TAG, "getSessionId: ${session.sessionId}")
                getAccountDetails(session.sessionId!!)
            } else
                return LoginStateEvent.SessionFailed("${session.statusMessage} code : ${session.statusCode}")
        } ?: LoginStateEvent.TimeOutError("Time Out!!")
    }

    suspend fun getAccountDetails(sessionId: String): LoginStateEvent {
        return withTimeoutOrNull(Constants.TIME_OUT_SHORT) {
            ServiceGenerator.apiService().getAccountDetails(Constants.API_KEY, sessionId)
        }?.let { account ->
            account.statusCode?.let {
                return LoginStateEvent.AccountDetailsFailed(
                    account.statusCode!!,
                    account.statusMessage!!
                )
            } ?: return LoginStateEvent.Success(account)
        } ?: LoginStateEvent.TimeOutError("Time Out!!")
    }
}