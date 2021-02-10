package com.nima.tmdb.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nima.tmdb.models.login.LoginInfo
import com.nima.tmdb.models.login.LoginResponse
import com.nima.tmdb.repositories.LoginRepository
import com.nima.tmdb.requests.wrapper.ApiWrapper
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
    private val repository : LoginRepository
):ViewModel() {
    private val _login = MutableLiveData<ApiWrapper<LoginResponse>>()
    val login: LiveData<ApiWrapper<LoginResponse>>
        get() = _login

    fun login(loginInfo: LoginInfo, apiKey: String) =
        viewModelScope.launch {
            _login.value = repository.login(loginInfo, apiKey)
        }
}