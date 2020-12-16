package com.nima.tmdb.login

import android.util.Log
import com.nima.tmdb.login.state.LoginStateEvent
import com.nima.tmdb.models.login.Login
import com.nima.tmdb.models.login.RequestToken
import com.nima.tmdb.requests.ServiceGenerator
import com.nima.tmdb.utils.Constants
import com.nima.tmdb.utils.Constants.TIME_OUT_SHORT
import kotlinx.coroutines.withTimeoutOrNull

class Authenticate {
    suspend fun requestToken(): LoginStateEvent {
        return withTimeoutOrNull(TIME_OUT_SHORT) {
            val token = ServiceGenerator.apiService().getNewToken(Constants.API_KEY)
            Log.d("TAG", "authentication: ${token.requestToken}")
            token
        }?.let { token ->
            return if (token.success)
                login(token.requestToken!!)
            else
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
    val login = Login("nimaabdpoor", "upf5YwB6@CXYiER", requestToken)
    return withTimeoutOrNull(TIME_OUT_SHORT) {
        ServiceGenerator.apiService().login(login, Constants.API_KEY)
//            Log.d("s;adlkfklasdflka", "authentication: ${loginResponse.success}")
//            Log.d("s;adlkfklasdflka", "authentication: ${loginResponse.expiresAt}")
//            Log.d("s;adlkfklasdflka", "authentication: ${loginResponse.requestToken}")
    }?.let { loginResponse ->
        if (loginResponse.success)
            getSessionId(loginResponse.requestToken!!)
        else
            LoginStateEvent.LoginFailed(loginResponse.statusMessage!!)
    } ?: LoginStateEvent.TimeOutError("Time Out!!")
}

private suspend fun getSessionId(_requestToken: String): LoginStateEvent {
    val requestToken = RequestToken(_requestToken)
    return withTimeoutOrNull(TIME_OUT_SHORT) {
        ServiceGenerator.apiService().getSessionId(requestToken, Constants.API_KEY)
    }?.let { session ->
        if (session.success)
            getAccountDetails(session.sessionId!!)
        else
            return LoginStateEvent.SessionFailed("${session.statusMessage} code : ${session.statusCode}")
    } ?: LoginStateEvent.TimeOutError("Time Out!!")
}

fun getAccountDetails(sessionId: String): LoginStateEvent {

}
}