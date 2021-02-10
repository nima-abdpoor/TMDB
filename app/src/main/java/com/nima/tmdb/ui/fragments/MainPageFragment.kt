package com.nima.tmdb.ui.fragments

import android.animation.ObjectAnimator
import android.graphics.Path
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nima.tmdb.R
import com.nima.tmdb.database.MyDao
import com.nima.tmdb.requests.wrapper.ApiWrapper
import com.nima.tmdb.utils.Constants.API_KEY
import com.nima.tmdb.viewModels.AuthenticationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_base.*
import javax.inject.Inject


@AndroidEntryPoint
class MainPageFragment : Fragment(R.layout.fragment_main_page) {

    @Inject
    lateinit var dao : MyDao

    private val TAG: String = "MainPageFragment"
    //private val userInfo = UserInfo(requireContext())

    private val viewModel: AuthenticationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getToken()
        //findNavController().navigate(R.id.action_mainPageFragment_to_movieListFragment)
    }

    private fun getToken() {
        Log.d(TAG, "subscribeOnTokenObserver :getToken Called")
        viewModel.getToken(API_KEY)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeOnTokenObserver()
        subscribeOnLoginObserver()
        subscribeOnSessionId()
    }

    private fun subscribeOnTokenObserver() {
        viewModel.getToken.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiWrapper.Success -> {
                    handleLogin(response.data?.requestToken)
                    Log.d(TAG, "subscribeOnTokenObserver :success ${response.data}")
                }
                is ApiWrapper.ApiError -> {
                    // TODO: 2/9/2021 show view that says : something went wrong!!
                    Log.d(TAG, "subscribeOnTokenObserver:api ${response.totalError}")
                }
                is ApiWrapper.NetworkError -> {
                    // TODO: 2/9/2021 show view that says :Please Check Your Connectivity NO INTERNET
                    Log.d(TAG, "subscribeOnTokenObserver:net ${response.message}")
                }
                is ApiWrapper.UnknownError -> {
                    // TODO: 2/9/2021 show view that says : failed to connect TMDB please make sure you are connected to internet
                    Log.d(TAG, "subscribeOnTokenObserver:unKnown ${response.message}")
                }
            }
        }
    }

    private fun handleLogin(requestToken: String?) {
        requestToken?.let {
           val userInfo = viewModel.getUserInfo()
            if (userInfo != null &&
                userInfo.userName?.isNotEmpty() == true &&
                        userInfo.password?.isNotEmpty() == true
            ){
                viewModel.login(userInfo,requestToken, API_KEY)
            }else{
                // TODO: 2/10/2021 navigate to login Page
            }
        }
    }

    private fun subscribeOnLoginObserver() {
        viewModel.login.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiWrapper.Success -> {
                }
                is ApiWrapper.ApiError -> {
                }
                is ApiWrapper.NetworkError -> {
                }
                is ApiWrapper.UnknownError -> {
                }
            }
        }
    }

    private fun subscribeOnSessionId() {
        viewModel.sessionId.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiWrapper.Success -> {
                }
                is ApiWrapper.ApiError -> {
                }
                is ApiWrapper.NetworkError -> {
                }
                is ApiWrapper.UnknownError -> {
                }
            }
        }
    }

 //   private fun getLoginInfo(
//        username: String? = null,
//        password: String? = null,
//        requestToken: String
//    ): LoginInfo {
//       // username ?: return userInfo.getUserInfo(requestToken)
//        return LoginInfo(username, password!!, requestToken)
//        //return LoginInfo("nimaabdpoot", "upf5YwB6@CXYiER", requestToken)
//    }

    private fun animate() {
        val path = Path().apply {
            arcTo(0f, 0f, 100f, 100f, 270f, -180f, true)
            arcTo(100f, 100f, 100f, -100f, -100f, -180f, true)
            arcTo(0f, 0f, 100f, 100f, 270f, -180f, true)
        }
        val animator = ObjectAnimator.ofFloat(button, View.X, View.Y, path).apply {
            duration = 2000
            for (i in 1..3) {
                start()
            }
        }
    }
}