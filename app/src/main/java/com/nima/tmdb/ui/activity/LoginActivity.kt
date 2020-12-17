package com.nima.tmdb.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.nima.tmdb.R
import com.nima.tmdb.login.Authentication
import com.nima.tmdb.login.UserInfo
import com.nima.tmdb.login.state.LoginStateEvent
import com.nima.tmdb.login.state.log
import com.nima.tmdb.utils.toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private val userInfo = UserInfo(this)
    private val authentication = Authentication(this)
    private lateinit var loginStateEvent : LoginStateEvent
    private var username : String = ""
    private var password : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val requestToken = intent.getStringExtra(R.string.requestToken.toString())
        Log.d(TAG, "onCreate: $requestToken")


        login_button.setOnClickListener{
            username = username_field.text.toString()
            password =password_field.text.toString()
            requestToken?.let {
                login(username , password , requestToken)
                Log.d(TAG, "onCreate: $username ++ $password +++ $requestToken")
            }
        }
    }

    private fun login( username: String, password: String, requestToken: String) {
        CoroutineScope(Dispatchers.IO).launch {
            loginStateEvent = authentication.login(username , password , requestToken)
            manage(loginStateEvent)
        }
    }
    private suspend fun manage(loginStateEvent: LoginStateEvent) {
        withContext(Dispatchers.Main){
            when (loginStateEvent) {
                is LoginStateEvent.TimeOutError -> handleTimeOut(loginStateEvent.message)
                is LoginStateEvent.LoginFailed -> handleFailedLogin(loginStateEvent.code, loginStateEvent.message , loginStateEvent.requestToken)
                is LoginStateEvent.SessionFailed -> handleSession(loginStateEvent.message)
                is LoginStateEvent.Success -> handleSuccess(loginStateEvent.session)
            }
        }
    }

    private fun handleSuccess(session: String) {
        userInfo.saveUserInfo(username , password)
        "success".toast(this)
        val intent = Intent(this , MainActivity::class.java)
        intent.putExtra((R.string.requestToken).toString(),session)
        startActivity(intent)
    }

    private fun handleTimeOut(message: String) {
        message.toast(this)
//        TODO()
        //show error :not have internet connection
    }

    private fun handleFailedLogin(code: Int, message: String , requestToken : String) {
        if (code == 401)
            message.toast(this)
        else log(code , message , "Failed To login")
    }

    private fun handleSession(message: String) {
        log(null, message, "SessionFailed")
        //this will happens rarely
        //when API_KEY or RequestToken is Invalid
    }
}