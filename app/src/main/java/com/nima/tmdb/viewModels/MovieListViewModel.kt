package com.nima.tmdb.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nima.tmdb.models.Example
import com.nima.tmdb.repositories.MovieListRepository
import com.nima.tmdb.requests.wrapper.ApiWrapper
import kotlinx.coroutines.launch
import javax.inject.Inject


class MovieListViewModel @Inject constructor(
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