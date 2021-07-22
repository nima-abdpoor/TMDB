package com.nima.tmdb.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nima.tmdb.database.entities.UserInfo
import com.nima.tmdb.business.domain.model.login.LoginResponse
import com.nima.tmdb.business.domain.model.login.RequestToken
import com.nima.tmdb.business.domain.model.login.Session
import com.nima.tmdb.business.domain.model.login.Token
import com.nima.tmdb.repositories.AuthenticationRepository
import com.nima.tmdb.requests.wrapper.ApiWrapper
import kotlinx.coroutines.launch

class AuthenticationViewModel @ViewModelInject constructor(
    private val repository: AuthenticationRepository
) : ViewModel() {
    private val _token = MutableLiveData<ApiWrapper<Token>>()
    val getToken: LiveData<ApiWrapper<Token>>
        get() = _token

    private val _userInfo = MutableLiveData<UserInfo>()
    val userInfo: LiveData<UserInfo>
        get() = _userInfo

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

    fun login(username :String ,password :String,requestToken: String , apiKey: String) =
        viewModelScope.launch {
            _login.value = repository.login(username,password ,requestToken , apiKey)
        }

    fun getSessionId(requestToken : RequestToken, apiKey: String) =
        viewModelScope.launch {
            _sessionId.value = repository.getSessionId(requestToken,apiKey)
        }
}