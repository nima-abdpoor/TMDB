package com.nima.tmdb.framewrok.presentation.login

import androidx.hilt.lifecycle.ViewModelInject
import com.nima.tmdb.business.data.network.requests.wrapper.ApiWrapper
import com.nima.tmdb.business.domain.model.login.LoginInfo
import com.nima.tmdb.business.domain.model.login.LoginResponse
import com.nima.tmdb.business.domain.model.login.RequestToken
import com.nima.tmdb.business.interactors.login.LoginInteractors
import com.nima.tmdb.framewrok.presentation.common.BaseViewModel
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel @ViewModelInject constructor(
    private val repository: LoginInteractors
) : BaseViewModel() {

    fun login(loginInfo: LoginInfo, apiKey: String): StateFlow<ApiWrapper<LoginResponse>> =
        execute {
            repository.login.login(loginInfo, apiKey)
        }

    fun getSessionId(requestToken: RequestToken, apiKey: String) = execute {
        repository.getSessionId.getSessionId(requestToken, apiKey)
    }
}