package com.nima.tmdb.ui.fragments

import android.animation.ObjectAnimator
import android.content.SharedPreferences
import android.graphics.Path
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nima.tmdb.R
import com.nima.tmdb.models.login.LoginResponse
import com.nima.tmdb.models.login.RequestToken
import com.nima.tmdb.models.login.Session
import com.nima.tmdb.requests.wrapper.ApiWrapper
import com.nima.tmdb.utils.Constants.API_KEY
import com.nima.tmdb.utils.toast
import com.nima.tmdb.viewModels.AuthenticationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main_page.*
import javax.inject.Inject


@AndroidEntryPoint
class MainPageFragment : Fragment(R.layout.fragment_main_page) {

    @Inject
    lateinit var pref: SharedPreferences

    private val TAG: String = "MainPageFragment"

    private val viewModel: AuthenticationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getToken()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animate()
        subscribeOnViewItems()
        subscribeOnTokenObserver()
        subscribeOnLoginObserver()
        subscribeOnSessionId()
    }

    private fun subscribeOnViewItems() {
        btn_mainPageF_tryAgain.setOnClickListener {
            animate()
            showErrorView(false)
            getToken()
        }
    }

    private fun getToken() {
        viewModel.getToken(API_KEY)
    }

    private fun subscribeOnTokenObserver() {
        viewModel.getToken.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiWrapper.Success -> handleLogin(response.data?.requestToken)
                is ApiWrapper.ApiError -> handleApiError(response.totalError)
                is ApiWrapper.NetworkError -> handleNetError(response.message)
                is ApiWrapper.UnknownError -> handleUnKnowError(response.message)
            }
        }
    }

    private fun subscribeOnLoginObserver() {
        viewModel.login.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiWrapper.Success -> handleSuccessLogin(response.data)
                is ApiWrapper.ApiError -> handleApiError(response.message)
                is ApiWrapper.NetworkError -> handleNetError(response.message)
                is ApiWrapper.UnknownError -> handleUnKnowError(response.message)
            }
        }
    }

    private fun subscribeOnSessionId() {
        viewModel.sessionId.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiWrapper.Success -> handelSuccessSession(response.data)
                is ApiWrapper.ApiError -> handleApiError(response.totalError)
                is ApiWrapper.NetworkError -> handleNetError(response.message)
                is ApiWrapper.UnknownError -> handleUnKnowError(response.message)
            }
        }
    }

    private fun handleSuccessLogin(data: LoginResponse?) {
        data?.let {
            val requestToken = it.requestToken?.let { it1 -> RequestToken(it1) }
            requestToken?.let { it1 -> viewModel.getSessionId(it1, API_KEY) }
        }
    }

    private fun handleLogin(requestToken: String?) {
        requestToken?.let {
            val userName = pref.getString(R.string.username.toString(), "")
            val password = pref.getString(R.string.password.toString(), "")
            if (userName?.isNotEmpty() == true && password?.isNotEmpty() == true) {
                viewModel.login(userName, password, requestToken, API_KEY)
            } else {
                val bundle = Bundle()
                bundle.putString(R.string.requestToken.toString(), requestToken)
                findNavController().navigate(R.id.action_mainPageFragment_to_loginFragment, bundle)
            }
        }
    }

    private fun handelSuccessSession(data: Session?) {
        data?.sessionId?.let {
            if (it.isNotEmpty()) {
                val bundle = Bundle()
                bundle.putString(R.string.sessionId.toString(), it)
                findNavController().navigate(
                    R.id.action_mainPageFragment_to_movieListFragment,
                    bundle
                )
            }
        }
    }


    private fun handleApiError(totalError: String?) {
        showErrorView(true)
        resources.getString(R.string.api_error_text).apply {
            showErrorView(true, this)
            this.toast(requireContext())
        }
        Log.d(TAG, "subscribeOnTokenObserver:api $totalError")
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

    private fun animate() {
        val path = Path().apply {
            arcTo(0f, 0f, 100f, 100f, 270f, -180f, true)
            arcTo(100f, 100f, 100f, -100f, -100f, -180f, true)
            arcTo(0f, 0f, 100f, 100f, 270f, -180f, true)
        }
        val animator = ObjectAnimator.ofFloat(btn_mainPageF_animation, View.X, View.Y, path).apply {
            duration = 2000
            for (i in 1..3) {
                start()
            }
        }
    }

    private fun showErrorView(show: Boolean, errorText: String? = null) {
        rtl_mainPageF_layout.isVisible = show
        btn_mainPageF_animation.isVisible = !show
        errorText?.let {
            txt_mainPageF_error.text = it
        }
    }
}