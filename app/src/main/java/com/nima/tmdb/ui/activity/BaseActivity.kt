package com.nima.tmdb.ui.activity

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Path
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.animation.PathInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.nima.tmdb.R
import com.nima.tmdb.login.Authentication
import com.nima.tmdb.login.UserInfo
import com.nima.tmdb.login.state.LoginStateEvent
import com.nima.tmdb.login.state.log
import com.nima.tmdb.utils.toast
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


const val TAG : String = "LoginStateEvent"

class BaseActivity : AppCompatActivity() {
    private val userInfo = UserInfo(this)
    private val authenticate = Authentication(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        animate()
        authenticate()
    }

    private fun animate() {
        // arcTo() and PathInterpolator only available on API 21+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val path = Path().apply {
                arcTo(0f, 0f, 1000f, 1000f, 270f, -180f, true)
            }
            val pathInterpolator = PathInterpolator(path)
            val animation = ObjectAnimator.ofFloat(button, "translationX", 100f).apply {
                interpolator = pathInterpolator
                start()
            }
        }



    }

    private fun authenticate() {
        CoroutineScope(Dispatchers.IO).launch {
            val loginStateEvent = authenticate.getRequestToken()
            manage(loginStateEvent)
        }
    }

    private suspend fun manage(loginStateEvent: LoginStateEvent) {
        withContext(Dispatchers.Main){
            when (loginStateEvent) {
                is LoginStateEvent.RequestTokenFailed -> handleFailed(loginStateEvent.statusCode , loginStateEvent.statusMessage)
                is LoginStateEvent.TimeOutError -> handleTimeOut(loginStateEvent.message)
                is LoginStateEvent.LoginFailed -> handleFailedLogin(loginStateEvent.code, loginStateEvent.message , loginStateEvent.requestToken)
                is LoginStateEvent.SessionFailed -> handleSession(loginStateEvent.message)
                is LoginStateEvent.Success -> handleSuccess(loginStateEvent.session)
            }
        }
    }

    private fun handleFailed(statusCode: Int, statusMessage: String) {
        //error happened in getting requestToken
        //usually happens for Invalid API_KEY
        Log.d(TAG, "handleFailed: $statusMessage with code : $statusCode")
    }

    private fun handleSuccess(session: String) {
        "success".toast(this)
        val intent = Intent(this , MainActivity::class.java)
        intent.putExtra((R.string.requestToken).toString(),session)
        startActivity(intent)
    }

    private fun handleTimeOut(message: String) {
        message.toast(this)
        if (userInfo.isUsernameEmptyOrNot()){
            //show error :not have internet connection

        }
        else{
            //offline mode
            handleSuccess("")
        }
    }

    private fun handleFailedLogin(code: Int, message: String , requestToken : String) {
        val intent = Intent(this , LoginActivity::class.java)
        log(null , requestToken , "askldfj")
        intent.putExtra((R.string.requestToken).toString(),requestToken)
        startActivity(intent)
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