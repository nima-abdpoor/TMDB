package com.nima.tmdb.framewrok.presentation.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nima.tmdb.business.domain.model.login.LoginInfo
import com.nima.tmdb.business.domain.model.login.LoginResponse
import com.nima.tmdb.business.domain.model.login.RequestToken
import com.nima.tmdb.business.domain.model.login.Session
import com.nima.tmdb.business.interactors.login.LoginInteractors
import com.nima.tmdb.business.data.network.requests.wrapper.ApiWrapper
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
    private val repository: LoginInteractors
) : ViewModel() {

    private val _login = MutableLiveData<ApiWrapper<LoginResponse>>()
    val login: LiveData<ApiWrapper<LoginResponse>>
        get() = _login

    private val _sessionId = MutableLiveData<ApiWrapper<Session>>()
    val sessionId: LiveData<ApiWrapper<Session>>
        get() = _sessionId

    fun login(loginInfo: LoginInfo, apiKey: String) =
        viewModelScope.launch {
            _login.value = repository.login.login(loginInfo, apiKey)
        }

    fun getSessionId(requestToken: RequestToken, apiKey: String) =
        viewModelScope.launch {
            _sessionId.value = repository.getSessionId.getSessionId(requestToken, apiKey)
        }
}