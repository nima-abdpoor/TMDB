package com.nima.tmdb.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.nima.tmdb.R
import com.nima.tmdb.sync.CheckNetwork
import com.nima.tmdb.sync.SyncStates
import com.nima.tmdb.utils.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


const val TAG : String = "BaseActivity"

class BaseActivity : AppCompatActivity() {
    private val authenticate = CheckNetwork()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        authenticate()
    }

    private fun authenticate() {
        CoroutineScope(Dispatchers.IO).launch {
            val syncState = authenticate.getRequestToken()
            manageStates(syncState)
        }
    }

    private suspend fun manageStates(syncState: SyncStates) {
        when (syncState) {
            is SyncStates.Failed -> handleFailed(syncState.statusCode, syncState.statusMessage)
            is SyncStates.TimeOutError -> handleTimeOut(syncState.message)
            is SyncStates.Success -> handleSuccess(syncState.requestToken)
        }
    }

    private fun handleFailed(statusCode: Int, statusMessage: String) {
        //error happened in getting requestToken
        //usually happens for Invalid API_KEY
        Log.d(TAG, "handleFailed: $statusMessage with code : $statusCode")
    }

    private fun handleSuccess(requestToken: String) {
        val intent = Intent(this , MainActivity::class.java)
        intent.putExtra((R.string.requestToken).toString(),requestToken)
        startActivity(intent)
    }

    private fun handleTimeOut(message: String) {
        message.toast(this)
//        TODO()
        //show error :not have internet connection
    }
}