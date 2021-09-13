package com.nima.tmdb.framewrok.presentation.createdlist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nima.tmdb.business.domain.model.account.lists.CreatedLists
import com.nima.tmdb.business.interactors.createdList.CreatedListInteractors
import com.nima.tmdb.requests.wrapper.ApiWrapper
import kotlinx.coroutines.launch

class CreatedListViewModel @ViewModelInject constructor(
    private val repository: CreatedListInteractors
) : ViewModel() {
    private val _createdLists = MutableLiveData<ApiWrapper<CreatedLists>>()
    val createdLists: LiveData<ApiWrapper<CreatedLists>>
        get() = _createdLists

    fun getCreatedLists(
        accountId: String,
        apiKey: String,
        sessionId: String,
        language: String?,
        page: Int?
    ) =
        viewModelScope.launch {
            _createdLists.value =
                repository.getCreatedList.getCreatedLists(
                    accountId,
                    sessionId,
                    apiKey,
                    language,
                    page
                )
        }
}