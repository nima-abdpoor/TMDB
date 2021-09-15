package com.nima.tmdb.framewrok.presentation.createdlist

import androidx.hilt.lifecycle.ViewModelInject
import com.nima.tmdb.business.interactors.createdList.CreatedListInteractors
import com.nima.tmdb.framewrok.presentation.common.BaseViewModel

class CreatedListViewModel @ViewModelInject constructor(
    private val repository: CreatedListInteractors
) : BaseViewModel() {

    fun getCreatedLists(
        accountId: String,
        apiKey: String,
        sessionId: String,
        language: String?,
        page: Int?
    ) = execute {
        repository.getCreatedList.getCreatedLists(
            accountId,
            sessionId,
            apiKey,
            language,
            page
        )
    }
}