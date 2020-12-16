package com.nima.tmdb.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nima.tmdb.R
import com.nima.tmdb.login.Authenticate
import com.nima.tmdb.login.state.LoginStateEvent.*
import com.nima.tmdb.login.state.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BaseActivity : AppCompatActivity() {
    private val authenticate = Authenticate()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        authenticate()
    }

    private fun authenticate() {
        CoroutineScope(Dispatchers.IO).launch {
            val loginStateEvent = authenticate.requestToken()
            when (loginStateEvent) {
                is RequestTokenFailure -> {
                    log(
                        loginStateEvent.statusCode,
                        loginStateEvent.statusMessage,
                        "RequestTokenFailure"
                    )
                }
                is AccountDetailsFailed -> {
                    log(
                        loginStateEvent.statusCode,
                        loginStateEvent.statusMessage,
                        "AccountDetailsFailed"
                    )
                }
                is TimeOutError -> timeOut(loginStateEvent.message)
                is LoginFailed -> {
                    handleFailedLogin(loginStateEvent.code,loginStateEvent.message)
                }
                is SessionFailed -> {
                    log(null, loginStateEvent.message, "SessionFailed")
                }
                is Success -> {
                    loginStateEvent.account.log(com.nima.tmdb.login.state.TAG)
                }
            }
        }
    }

    private fun handleFailedLogin(code: Int, message: String) {
        log(null, message, "LoginFailed")
        if (code == 400) //login page
            else //login page with error
//        TODO("Not yet implemented")
        //show login page proper message
    }

    private fun timeOut(message: String) {
        log(null, message, "TimeOutError")
//        TODO()
        //show error :not have internet connection
    }
}