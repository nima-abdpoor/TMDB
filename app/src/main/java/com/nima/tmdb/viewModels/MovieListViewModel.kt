package com.nima.tmdb.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nima.tmdb.business.domain.model.Example
import com.nima.tmdb.repositories.MovieListRepository
import com.nima.tmdb.requests.wrapper.ApiWrapper
import kotlinx.coroutines.launch


class MovieListViewModel @ViewModelInject constructor(
    private val repository: MovieListRepository
) : ViewModel() {

    private val _movieList = MutableLiveData<ApiWrapper<Example>>()
    val movieList: LiveData<ApiWrapper<Example>>
        get() = _movieList


    fun setMovie(key: String, language: String?, query: String, page: Int, include_adult: Boolean) =
        viewModelScope.launch {
            _movieList.value = repository.searchMovieAPI(key, language, query, page, include_adult)
        }
}