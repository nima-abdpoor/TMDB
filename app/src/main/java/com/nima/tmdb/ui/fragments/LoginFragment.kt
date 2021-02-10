package com.nima.tmdb.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.nima.tmdb.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment :Fragment(R.layout.login_fragment){

    private val TAG = "LoginFragment"
    private var requestToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestToken = arguments?.getString(R.string.requestToken.toString(),"null")
        Log.d(TAG, "onCreate: $requestToken")
    }
}