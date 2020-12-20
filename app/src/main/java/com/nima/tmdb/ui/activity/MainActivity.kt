package com.nima.tmdb.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import com.nima.tmdb.R
import com.nima.tmdb.login.Authentication
import com.nima.tmdb.login.state.LoginStateEvent
import com.nima.tmdb.login.state.log
import com.nima.tmdb.models.login.account.Account
import com.nima.tmdb.utils.toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var bundle : Bundle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sessionId = intent.getStringExtra(R.string.requestToken.toString())
        sessionId?.let {
            if (sessionId.isEmpty()) {
                //offline Mode
            }
            else{
                bundle = Bundle()
                bundle.putString(R.string.sessionId.toString() , sessionId)
                findNavController(R.id.nav_host_fragment).setGraph(R.navigation.nav_graph ,bundle )
            }
        }
        Log.d(TAG, "onCreate: $sessionId")
    }
}