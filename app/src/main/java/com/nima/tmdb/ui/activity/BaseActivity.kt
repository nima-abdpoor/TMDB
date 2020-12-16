package com.nima.tmdb.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nima.tmdb.R
import com.nima.tmdb.login.Authenticate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BaseActivity : AppCompatActivity() {
    val authenticate = Authenticate()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        CoroutineScope(Dispatchers.IO).launch{
            authenticate.requestToken()
        }
    }
}