package com.nima.tmdb.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nima.tmdb.R
import com.nima.tmdb.models.login.LoginInfo
import com.nima.tmdb.requests.wrapper.ApiWrapper
import com.nima.tmdb.utils.Constants.API_KEY
import com.nima.tmdb.viewModels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.login_fragment.*

@AndroidEntryPoint
class LoginFragment :Fragment(R.layout.login_fragment){

    private val viewModel: LoginViewModel by viewModels()

    private val TAG = "LoginFragment"
    private var requestToken: String? = null
    private var userName :String = ""
    private var password :String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestToken = arguments?.getString(R.string.requestToken.toString(),"null")
        Log.d(TAG, "onCreate: $requestToken")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeOnViewItems()
        subscribeOnLoginOnObserver()
    }

    private fun subscribeOnLoginOnObserver() {
        viewModel.login.observe(viewLifecycleOwner){response ->
            when (response) {
                is ApiWrapper.Success -> {
                    Log.d(TAG, "subscribeOnLoginOnObserver :success ${response.data}")
                }
                is ApiWrapper.ApiError -> {
                    // TODO: 2/9/2021 show view that says : something went wrong!!
                    Log.d(TAG, "subscribeOnLoginOnObserver:api ${response.totalError}")
                }
                is ApiWrapper.NetworkError -> {
                    // TODO: 2/9/2021 show view that says :Please Check Your Connectivity NO INTERNET
                    Log.d(TAG, "subscribeOnLoginOnObserver:net ${response.message}")
                }
                is ApiWrapper.UnknownError -> {
                    // TODO: 2/9/2021 show view that says : failed to connect TMDB please make sure you are connected to internet
                    Log.d(TAG, "subscribeOnLoginOnObserver :unKnown ${response.message}")
                }
            }
        }
    }

    private fun subscribeOnViewItems() {
        btn_loginF_login.setOnClickListener {
            subscribeOnLoginButton()
        }
    }

    private fun subscribeOnLoginButton() {
        userName = et_loginF_username.text.toString()
        password = et_loginF_password.text.toString()
        if (userName.isNotEmpty() && password.isNotEmpty()){
            login()
        }
        else{
            Toast.makeText(requireContext(),"UserName or Password must have value",Toast.LENGTH_SHORT).show()
        }
    }

    private fun login() {
        requestToken?.let {token ->
            val loginInfo = LoginInfo(userName,password,token)
            viewModel.login(loginInfo,API_KEY)
        }
    }
}