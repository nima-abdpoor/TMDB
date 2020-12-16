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
            when (val loginStateEvent = authenticate.requestToken()) {
                is RequestTokenFailure -> {
                    log(loginStateEvent.statusCode, loginStateEvent.statusMessage)
                }
                is AccountDetailsFailed -> {
                    log(loginStateEvent.statusCode, loginStateEvent.statusMessage)
                }
                is TimeOutError -> {
                    log(null, loginStateEvent.message)
                }
                is LoginFailed -> {
                    log(null, loginStateEvent.message)
                }
                is SessionFailed -> {
                    log(null, loginStateEvent.message)
                }
                is Success -> {
                    loginStateEvent.account.log(com.nima.tmdb.login.state.TAG)
                }
            }
        }
    }
}