package com.nima.tmdb.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.nima.tmdb.R
import com.nima.tmdb.models.Login
import com.nima.tmdb.models.RequestToken
import com.nima.tmdb.requests.ServiceGenerator
import com.nima.tmdb.utils.Constants.API_KEY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        CoroutineScope(Dispatchers.IO).launch{
           authentication()
        }
    }

    private suspend fun authentication() {
        withTimeoutOrNull(3000L){
            ServiceGenerator.apiService().getNewToken(API_KEY)
            //Log.d("TAG", "authentication: ${token.requestToken}")
        }?.let {token ->
           if (token.success){
               login(token.requestToken)
           }
        }
    }

    private suspend fun login(requestToken: String) {
        val login = Login("nimaabdpoor","upf5YwB6@CXYiER",requestToken)
        val loginResponse = withTimeoutOrNull(3000L){
             ServiceGenerator.apiService().login(login, API_KEY)
//            Log.d("s;adlkfklasdflka", "authentication: ${loginResponse.success}")
//            Log.d("s;adlkfklasdflka", "authentication: ${loginResponse.expiresAt}")
//            Log.d("s;adlkfklasdflka", "authentication: ${loginResponse.requestToken}")
        }
        loginResponse?.let {loginResponse ->
            if (loginResponse.success){
                getSessionId(loginResponse.requestToken!!)
            }
        }
    }

    private suspend fun getSessionId(_requestToken: String) {
        val requestToken = RequestToken(_requestToken)
         withTimeoutOrNull(3000L){
             val session =ServiceGenerator.apiService().getSessionId(requestToken, API_KEY)
             if (session?.success)
            Log.d("s;adlkfklasdflka", "authentication: ${session.sessionId}")
        }
    }
}