package com.nima.tmdb.framewrok.presentation.detail

import androidx.hilt.lifecycle.ViewModelInject
import com.nima.tmdb.business.interactors.details.DetailsInteractors
import com.nima.tmdb.framewrok.presentation.common.BaseViewModel

class MovieDetailsViewModel @ViewModelInject constructor(
    private val repository: DetailsInteractors
) : BaseViewModel() {

    fun setMovieID(movieId: Int, apiKey: String, language: String) = execute {
        repository.getMovieById.searchMovieAPI(movieId, apiKey, language)
    }
}