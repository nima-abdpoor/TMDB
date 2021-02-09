package com.nima.tmdb.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nima.tmdb.models.login.*
import com.nima.tmdb.repositories.AuthenticationRepository
import com.nima.tmdb.requests.wrapper.ApiWrapper
import kotlinx.coroutines.launch

class AuthenticationViewModel @ViewModelInject constructor(
    private val repository: AuthenticationRepository
) : ViewModel() {
    private val _token = MutableLiveData<ApiWrapper<Token>>()
    val getToken: LiveData<ApiWrapper<Token>>
        get() = _token

    private val _login = MutableLiveData<ApiWrapper<LoginResponse>>()
    val login: LiveData<ApiWrapper<LoginResponse>>
        get() = _login

    private val _sessionId = MutableLiveData<ApiWrapper<Session>>()
    val sessionId: LiveData<ApiWrapper<Session>>
        get() = _sessionId

    fun getToken(apiKey: String) =
        viewModelScope.launch {
            _token.value = repository.requestToken(apiKey)
        }

    fun login(loginInfo: LoginInfo,apiKey: String) =
        viewModelScope.launch {
            _login.value = repository.login(loginInfo,apiKey)
        }

    fun getSessionId(requestToken : RequestToken, apiKey: String) =
        viewModelScope.launch {
            _sessionId.value = repository.getSessionId(requestToken,apiKey)
        }
}