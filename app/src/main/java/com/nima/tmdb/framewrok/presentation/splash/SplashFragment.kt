package com.nima.tmdb.framewrok.presentation.splash

import android.animation.ValueAnimator
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Button
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.nima.tmdb.R
import com.nima.tmdb.business.data.network.requests.wrapper.ApiWrapper
import com.nima.tmdb.business.domain.model.login.*
import com.nima.tmdb.databinding.FragmentSplashBinding
import com.nima.tmdb.utils.Constants.API_KEY
import com.nima.tmdb.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    @Inject
    lateinit var pref: SharedPreferences

    private val TAG: String = "MainPageFragment"
    lateinit var animationButton: Button
    var duration = 1000L

    private val viewModel: SplashViewModel by viewModels()

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeOnViewItems()
        getToken()
        animate()
    }

    private fun subscribeOnViewItems() {
        animationButton = view?.findViewById(R.id.btn_firstPageF_animation)!!
        binding.btnFirstPageFTryAgain.setOnClickListener {
            animate()
            showErrorView(false)
            getToken()
        }
    }

    private fun getToken() {
        val result = viewModel.getToken(API_KEY)
        subscribeOnTokenObserver(result)
    }

    private fun subscribeOnTokenObserver(result: StateFlow<ApiWrapper<Token>>) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                result.collect { response ->
                    when (response) {
                        is ApiWrapper.Success -> handleLogin(response.data?.requestToken)
                        is ApiWrapper.ApiError -> handleApiError(response.totalError)
                        is ApiWrapper.NetworkError -> handleNetError(response.message)
                        is ApiWrapper.UnknownError -> handleUnKnowError(response.message)
                        is ApiWrapper.Loading -> Log.d(TAG, "subscribeOnTokenObserver: Loading")
                    }
                }
            }
        }
    }

    private fun subscribeOnLoginObserver(result: StateFlow<ApiWrapper<LoginResponse>>) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                result.collect { response ->
                    when (response) {
                        is ApiWrapper.Success -> handleSuccessLogin(response.data)
                        is ApiWrapper.ApiError -> handleApiError(response.message)
                        is ApiWrapper.NetworkError -> handleNetError(response.message)
                        is ApiWrapper.UnknownError -> handleUnKnowError(response.message)
                        is ApiWrapper.Loading -> Log.d(TAG, "subscribeOnLoginObserver: Loading")
                    }
                }
            }
        }
    }

    private fun subscribeOnSessionId(result: StateFlow<ApiWrapper<Session>>) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                result.collect { response ->
                    when (response) {
                        is ApiWrapper.Success -> handelSuccessSession(response.data)
                        is ApiWrapper.ApiError -> handleApiError(response.totalError)
                        is ApiWrapper.NetworkError -> handleNetError(response.message)
                        is ApiWrapper.UnknownError -> handleUnKnowError(response.message)
                        is ApiWrapper.Loading -> Log.d(TAG, "subscribeOnSessionId: Loading")
                    }
                }
            }
        }
    }

    private fun handleSuccessLogin(data: LoginResponse?) {
        data?.let {
            val requestToken = it.requestToken?.let { it1 -> RequestToken(it1) }
            requestToken?.let { it1 ->
                val result = viewModel.getSessionId(it1, API_KEY)
                subscribeOnSessionId(result)
            }
        }
    }

    private fun handleLogin(requestToken: String?) {
        requestToken?.let {
            val userName = pref.getString(R.string.username.toString(), "")
            val password = pref.getString(R.string.password.toString(), "")
            if (userName?.isNotEmpty() == true && password?.isNotEmpty() == true) {
                val loginInfo = LoginInfo(userName, password, requestToken)
                val result = viewModel.login(loginInfo, API_KEY)
                subscribeOnLoginObserver(result)
            } else {
                val bundle = Bundle()
                bundle.putString(R.string.requestToken.toString(), requestToken)
                findNavController().navigate(R.id.action_firstPageFragment_to_loginFragment, bundle)
            }
        }
    }

    private fun handelSuccessSession(data: Session?) {
        data?.sessionId?.let {
            if (it.isNotEmpty()) {
                val bundle = Bundle()
                bundle.putString(R.string.sessionId.toString(), it)
                findNavController().navigate(
                    R.id.action_firstPageFragment_to_mainPageFragment,
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
        val valueAnimator0 = ValueAnimator.ofFloat(0f, -40f)
        val valueAnimator1 = ValueAnimator.ofFloat(0f, 5 * 360f)
        val valueAnimator2 = ValueAnimator.ofFloat(0f, 4 * 360f)
        val valueAnimator3 = ValueAnimator.ofFloat(0f, 3 * 360f)
        val valueAnimator4 = ValueAnimator.ofFloat(0f, 2 * 180f)
        val valueAnimator5 = ValueAnimator.ofFloat(0f, 2 * 180f)
        val valueAnimator6 = ValueAnimator.ofFloat(0f, 1 * 180f)
        val valueAnimators = listOf<ValueAnimator>(
            valueAnimator1,
            valueAnimator2,
            valueAnimator3,
            valueAnimator4,
            valueAnimator5,
            valueAnimator6
        )
        valueAnimator0.addUpdateListener {
            val value = it.animatedValue as Float
            animationButton.rotation = value
        }
        valueAnimator0.interpolator = LinearInterpolator()
        valueAnimator0.duration = 1500L
        valueAnimator1.addUpdateListener {
            val value = it.animatedValue as Float
            animationButton.rotation = value
        }
        valueAnimator2.addUpdateListener {
            val value = it.animatedValue as Float
            animationButton.rotation = value
        }
        valueAnimator3.addUpdateListener {
            val value = it.animatedValue as Float
            animationButton.rotation = value
        }
        valueAnimator4.addUpdateListener {
            val value = it.animatedValue as Float
            animationButton.rotation = value
        }
        valueAnimator5.addUpdateListener {
            val value = it.animatedValue as Float
            animationButton.rotation = value
        }
        valueAnimator6.addUpdateListener {
            val value = it.animatedValue as Float
            animationButton.rotation = value
        }
        for (i in valueAnimators) {
            i.interpolator = LinearInterpolator()
            i.duration = duration / 2
        }
        valueAnimator0.start()
        valueAnimator0.doOnEnd {
            valueAnimator6.start()
            valueAnimator6.doOnEnd {
                valueAnimator5.start()
                valueAnimator5.doOnEnd {
                    valueAnimator4.start()
                    valueAnimator4.doOnEnd {
                        valueAnimator3.start()
                        valueAnimator3.doOnEnd {
                            valueAnimator2.start()
                            valueAnimator2.doOnEnd {
                                valueAnimator1.doOnEnd {
                                    valueAnimator6.start()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showErrorView(show: Boolean, errorText: String? = null) {
        binding.rtlFirstPageFLayout.isVisible = show
        binding.btnFirstPageFAnimation.isVisible = !show
        errorText?.let {
            binding.txtFirstPageFError.text = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}