package com.nima.tmdb.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.nima.tmdb.R
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
            val token = ServiceGenerator.apiService().getNewToken(API_KEY)
            Log.d("TAG", "authentication: ${token.requestToken}")
        }
    }
}