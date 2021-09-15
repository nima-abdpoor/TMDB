package com.nima.tmdb.framewrok.presentation.splash

import androidx.hilt.lifecycle.ViewModelInject
import com.nima.tmdb.business.domain.model.login.LoginInfo
import com.nima.tmdb.business.domain.model.login.RequestToken
import com.nima.tmdb.business.interactors.splash.SplashInteractors
import com.nima.tmdb.framewrok.presentation.common.BaseViewModel

class SplashViewModel @ViewModelInject constructor(
    private val repository: SplashInteractors
) : BaseViewModel() {

    fun getToken(apiKey: String) = execute {
        repository.token.getToken(apiKey)
    }

    fun login(loginInfo: LoginInfo, apiKey: String) = execute {
        repository.login.login(loginInfo, apiKey)
    }

    fun getSessionId(requestToken: RequestToken, apiKey: String) = execute {
        repository.getSessionId.getSessionId(requestToken, apiKey)
    }
}