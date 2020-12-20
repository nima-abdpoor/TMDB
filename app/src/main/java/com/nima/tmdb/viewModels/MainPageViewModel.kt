package com.nima.tmdb.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nima.tmdb.database.DatabaseConnection
import com.nima.tmdb.database.TMDBDatabase
import com.nima.tmdb.database.getDatabase
import com.nima.tmdb.models.login.account.Account
import com.nima.tmdb.models.login.account.asDatabaseAccount
import com.nima.tmdb.repositories.MainPageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainPageViewModel constructor(application: Application) : ViewModel(),
    DatabaseConnection<Account> {
    private val application = application
    private lateinit var database : TMDBDatabase

    class Factory(private val _application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainPageViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainPageViewModel(_application) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    private var sessionId: String = ""
    private val mainPageRepository = MainPageRepository
    val query: MutableLiveData<Account> = MutableLiveData()

    fun setSessionId(_sessionId: String) {
        this.sessionId = _sessionId
        searchAccount()
    }

    private fun searchAccount() {
        viewModelScope.launch {
            query.value = mainPageRepository.getAccountDetails(sessionId)
        }
    }

    override fun save(account: Account) {
        CoroutineScope(Dispatchers.IO).launch {
            database = getDatabase(application)
            database.accountDao.insertAll(account.asDatabaseAccount())
        }
    }

    override fun load(): Account {
       TODO()
    }
}