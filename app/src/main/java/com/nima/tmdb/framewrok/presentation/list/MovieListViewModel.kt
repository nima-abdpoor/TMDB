package com.nima.tmdb.framewrok.presentation.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nima.tmdb.business.data.network.requests.wrapper.ApiWrapper
import com.nima.tmdb.business.domain.model.Example
import com.nima.tmdb.business.interactors.list.ListInteractors
import com.nima.tmdb.utils.Constants.TIME_OUT_MEDIUM
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn


class MovieListViewModel @ViewModelInject constructor(
    private val repository: ListInteractors
) : ViewModel() {

    fun setMovie(
        key: String,
        language: String?,
        query: String,
        page: Int,
        include_adult: Boolean
    ): StateFlow<ApiWrapper<Example>> {
        val result: StateFlow<ApiWrapper<Example>> = flow {
            val re =
                repository.searchMovie.searchMovieAPI(key, language, query, page, include_adult)
            emit(re)
        }.stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(TIME_OUT_MEDIUM),
            initialValue = ApiWrapper.Loading("loading")
        )
        return result
    }
}