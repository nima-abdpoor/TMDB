package com.nima.tmdb.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nima.tmdb.R
import com.nima.tmdb.login.Authentication
import com.nima.tmdb.login.state.LoginStateEvent
import com.nima.tmdb.login.state.log
import com.nima.tmdb.models.login.account.Account
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val authentication = Authentication(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val requestToken = savedInstanceState?.get(R.string.requestToken.toString()).toString()
        authenticate(requestToken)
    }

    private fun authenticate(requestToken: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val loginStateEvent = authentication.login(requestToken)
            manage(loginStateEvent)
        }
    }

    private suspend fun manage(loginStateEvent: LoginStateEvent) {
        withContext(Dispatchers.Main){
            when (loginStateEvent) {
                is LoginStateEvent.AccountDetailsFailed ->handleUnknownFailure(loginStateEvent.statusCode,loginStateEvent.statusMessage,"AccountDetailsFailed")
                is LoginStateEvent.TimeOutError -> timeOut(loginStateEvent.message)
                is LoginStateEvent.LoginFailed -> handleFailedLogin(loginStateEvent.code, loginStateEvent.message)
                is LoginStateEvent.SessionFailed -> handleSession(loginStateEvent.message)
                is LoginStateEvent.Success -> handleSuccess(loginStateEvent.account)
            }
        }
    }

    private fun handleSuccess(account: Account) {
        TODO("Not yet implemented")
    }

    private fun handleFailedLogin(code: Int, message: String) {
        TODO("Not yet implemented")
    }

    private fun timeOut(message: String) {
        TODO("Not yet implemented")
    }

    private fun handleUnknownFailure(statusCode: Int, statusMessage: String , methodName : String) {
        log(statusCode, statusMessage, methodName)
    }

    private fun handleSession(message: String) {
        log(null, message, "SessionFailed")
        TODO("Not yet implemented")
    }
}