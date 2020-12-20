package com.nima.tmdb.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.nima.tmdb.database.DatabaseConnection
import com.nima.tmdb.database.TMDBDatabase
import com.nima.tmdb.database.getDatabase
import com.nima.tmdb.models.login.account.Account
import com.nima.tmdb.models.login.account.asDatabaseAccount
import com.nima.tmdb.repositories.MainPageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val TAG :String = "MainPageViewModel"

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
            val account = mainPageRepository.getAccountDetails(sessionId)
            query.value = account
            save(account)
        }
    }

    override fun save(account: Account) {
        CoroutineScope(Dispatchers.IO).launch {
            database = getDatabase(application)
            database.accountDao.insertAll(account.asDatabaseAccount())
        }
    }

    override fun load() {
        CoroutineScope(Dispatchers.IO).launch {
            database = getDatabase(application)
            val account = database.accountDao.getAccount().asLoginAccount()
            withContext(Dispatchers.Main){
                query.value = account
                Log.d(TAG, "load: ${account.id}")
                Log.d(TAG, "load: ${account.username}")
            }
        }
    }
}

private fun com.nima.tmdb.database.Account.asLoginAccount(): Account {
    this?.let {
        val account = it
        return Account(
            id = account.id,
            iso6391 = account.iso6391,
            iso31661 = account.iso31661,
            name = account.name,
            includeAdult = account.includeAdult,
            username = account.username
        )
    }
    return Account()
}
