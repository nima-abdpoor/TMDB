package com.nima.tmdb.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nima.tmdb.R
import com.nima.tmdb.database.MyDao
import com.nima.tmdb.models.login.LoginInfo
import com.nima.tmdb.models.login.LoginResponse
import com.nima.tmdb.models.login.RequestToken
import com.nima.tmdb.models.login.Session
import com.nima.tmdb.requests.wrapper.ApiWrapper
import com.nima.tmdb.utils.Constants.API_KEY
import com.nima.tmdb.utils.toast
import com.nima.tmdb.viewModels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment :Fragment(R.layout.fragment_login){

    @Inject
    lateinit var dao :MyDao
    @Inject
    lateinit var pref : SharedPreferences

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
        subscribeOnSessionId()
    }

    private fun subscribeOnLoginOnObserver() {
        viewModel.login.observe(viewLifecycleOwner){response ->
            when (response) {
                is ApiWrapper.Success ->handleSuccessLogin(response.data)
                is ApiWrapper.ApiError -> handleApiError(response.totalError)
                is ApiWrapper.NetworkError -> handleNetError(response.message)
                is ApiWrapper.UnknownError -> handleUnKnowError(response.message)
            }
        }
    }
    private fun subscribeOnSessionId() {
        viewModel.sessionId.observe(viewLifecycleOwner){response ->
            when (response) {
                is ApiWrapper.Success ->handelSuccessSession(response.data)
                is ApiWrapper.ApiError -> handleApiError(response.totalError)
                is ApiWrapper.NetworkError -> handleNetError(response.message)
                is ApiWrapper.UnknownError -> handleUnKnowError(response.message)
            }
        }
    }



    private fun subscribeOnViewItems() {
        btn_loginF_login.setOnClickListener {
            subscribeOnLoginButton()
        }
        btn_loginPageF_tryAgain.setOnClickListener {
            showErrorView(false)
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
    private fun handleSuccessLogin(data: LoginResponse?) {
        data?.let {
            val requestToken = it.requestToken?.let { it1 -> RequestToken(it1) }
            requestToken?.let { it1 -> viewModel.getSessionId(it1, API_KEY) }
            saveUserData()
        }
    }

    private fun saveUserData() {
        CoroutineScope(Dispatchers.IO).launch {
            pref.edit()
                .putString(R.string.username.toString(),userName)
                .putString(R.string.password.toString(),password)
                .apply()
        }
    }

    private fun handelSuccessSession(data: Session?) {
        data?.sessionId?.let {
            if (it.isNotEmpty()){
                val bundle = Bundle()
                bundle.putString(R.string.sessionId.toString(),it)
                findNavController().navigate(R.id.action_loginFragment_to_movieListFragment,bundle)
            }
        }
    }

    private fun login() {
        requestToken?.let {token ->
            val loginInfo = LoginInfo(userName,password,token)
            viewModel.login(loginInfo,API_KEY)
        }
    }
    private fun handleApiError(totalError: String?) {
        resources.getString(R.string.invalid_username_password).apply {
            this.toast(requireContext())
        }
        Log.d(TAG, "handleApiError:api $totalError")
    }
    private fun handleNetError(message: String?) {
        resources.getString(R.string.check_your_connection).apply {
            showErrorView(true, this)
            this.toast(requireContext())
        }
        Log.d(TAG, "subscribeOnTokenObserver:net $message")
    }
    private fun handleUnKnowError(message: String?) {
        resources.getString(R.string.check_your_connection).apply {
            showErrorView(true, this)
            this.toast(requireContext())
        }
        Log.d(TAG, "subscribeOnTokenObserver:unKnown $message")
    }

    private fun showErrorView(show: Boolean, errorText: String? = null) {
        rtl_loginPageF_layout.isVisible = show
        lnl_loginPageF_layout.isVisible = !show
        errorText?.let {
            txt_loginPageF_error.text = it
        }
    }
}