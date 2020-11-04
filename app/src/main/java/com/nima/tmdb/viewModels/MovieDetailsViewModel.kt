package com.nima.tmdb.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.nima.tmdb.models.Details
import com.nima.tmdb.repositories.MovieDetailsRepository

class MovieDetailsViewModel : ViewModel() {

    private val movieDetailsRepositories : MovieDetailsRepository = MovieDetailsRepository
    private val _movieID : MutableLiveData<Int> = MutableLiveData()

    val searchMovieAPI: LiveData<Details> = Transformations
        .switchMap(_movieID){movieID ->
            movieDetailsRepositories.searchMovieAPI(movieID)
        }

    fun setMovieID(movieID: Int){
        if (movieID == _movieID.value){
            return
        }
        _movieID.value = movieID
    }
}