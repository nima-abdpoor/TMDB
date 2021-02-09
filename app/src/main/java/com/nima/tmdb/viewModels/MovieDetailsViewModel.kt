package com.nima.tmdb.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nima.tmdb.models.Details
import com.nima.tmdb.repositories.MovieDetailsRepository
import com.nima.tmdb.requests.wrapper.ApiWrapper
import kotlinx.coroutines.launch

class MovieDetailsViewModel @ViewModelInject constructor(
    private val repository: MovieDetailsRepository
) : ViewModel() {

    private val _movieDetails = MutableLiveData<ApiWrapper<Details>>()
    val movieDetails: LiveData<ApiWrapper<Details>>
        get() = _movieDetails


    fun setMovieID(movieId: Int, apiKey: String, language: String) =
        viewModelScope.launch {
            _movieDetails.value = repository.searchMovieAPI(movieId, apiKey, language)
        }
}