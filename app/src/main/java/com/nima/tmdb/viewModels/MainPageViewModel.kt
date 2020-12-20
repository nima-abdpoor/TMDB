package com.nima.tmdb.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nima.tmdb.models.login.account.Account
import com.nima.tmdb.repositories.MainPageRepository
import kotlinx.coroutines.launch

class MainPageViewModel : ViewModel(){

    private var sessionId : String = ""
    private val mainPageRepository = MainPageRepository
    val query : MutableLiveData<Account> = MutableLiveData()

    fun setSessionId(_sessionId : String){
        this.sessionId = _sessionId
        searchAccount()
    }

    private fun searchAccount() {
        viewModelScope.launch {
            query.value = mainPageRepository.getAccountDetails(sessionId)
        }
    }
}