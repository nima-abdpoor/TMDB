package com.nima.tmdb.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.nima.tmdb.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var bundle : Bundle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sessionId = intent.getStringExtra(R.string.requestToken.toString())
        navigate()
        sessionId?.let {it->
            if (sessionId.isEmpty()) {
                //offline Mode
            }
            else{
            }
        }
        Log.d(TAG, "onCreate: $sessionId")
    }

    private fun navigate(sessionId: String = "") {
        bundle = Bundle()
        bundle.putString(R.string.sessionId.toString() , sessionId)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController.setGraph(R.navigation.nav_graph ,bundle)
    }
}