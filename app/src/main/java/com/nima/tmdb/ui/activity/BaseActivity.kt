package com.nima.tmdb.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nima.tmdb.R
import com.nima.tmdb.login.Authenticate
import com.nima.tmdb.login.state.LoginStateEvent.*
import com.nima.tmdb.login.state.log
import com.nima.tmdb.models.login.account.Account
import com.nima.tmdb.utils.toast
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
                is RequestTokenFailure -> handleUnknownFailure(
                    loginStateEvent.statusCode, loginStateEvent.statusMessage , "requestToken")
                is AccountDetailsFailed ->handleUnknownFailure(loginStateEvent.statusCode,loginStateEvent.statusMessage,"AccountDetailsFailed")
                is TimeOutError -> timeOut(loginStateEvent.message)
                is LoginFailed -> handleFailedLogin(loginStateEvent.code, loginStateEvent.message)
                is SessionFailed -> handleSession(loginStateEvent.message)
                is Success -> handleSuccess(loginStateEvent.account)
            }
        }
    }

    private fun handleUnknownFailure(statusCode: Int, statusMessage: String , methodName : String) {
        log(statusCode, statusMessage, methodName)
    }

    private fun handleSession(message: String) {
        log(null, message, "SessionFailed")
        TODO("Not yet implemented")
    }

    private fun handleSuccess(account: Account) {
        account.log(com.nima.tmdb.login.state.TAG)
        TODO("Not yet implemented")
    }

    private fun handleFailedLogin(code: Int, message: String) {
        log(null, message, "LoginFailed")
        goToLoginPage()
        if (code == 400) {
            //null inputs in login variables
        } else {
            message.toast(this)
        }
    }

    private fun goToLoginPage() {
        TODO()
    }

    private fun timeOut(message: String) {
        log(null, message, "TimeOutError")
        message.toast(this)
        TODO()
        //show error :not have internet connection
    }
}