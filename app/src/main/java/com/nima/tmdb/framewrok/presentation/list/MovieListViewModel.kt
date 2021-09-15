package com.nima.tmdb.framewrok.presentation.list

import androidx.hilt.lifecycle.ViewModelInject
import com.nima.tmdb.business.data.network.requests.wrapper.ApiWrapper
import com.nima.tmdb.business.domain.model.Example
import com.nima.tmdb.business.interactors.list.ListInteractors
import com.nima.tmdb.framewrok.presentation.common.BaseViewModel
import kotlinx.coroutines.flow.StateFlow


class MovieListViewModel @ViewModelInject constructor(
    private val repository: ListInteractors
) : BaseViewModel() {

    fun setMovie(
        key: String,
        language: String?,
        query: String,
        page: Int,
        include_adult: Boolean
    ): StateFlow<ApiWrapper<Example>> = execute {
        repository.searchMovie.searchMovieAPI(key, language, query, page, include_adult)
    }
}