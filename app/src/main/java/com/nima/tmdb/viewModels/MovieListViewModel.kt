package com.nima.tmdb.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.nima.tmdb.models.Example
import com.nima.tmdb.repositories.MovieListRepository



class MovieListViewModel : ViewModel() {
    private val movieRepository: MovieListRepository = MovieListRepository
    private val _query :MutableLiveData<String> = MutableLiveData()
    private var _page : Int = 1

    var isMovieRetrieved = false

    fun searchNextPage() {
        movieRepository.searchNextQuery()
    }


    val searchMovieAPI: LiveData<Example> = Transformations
        .switchMap(_query){query ->
            movieRepository.searchMovieAPI(query,_page)
        }

    fun setMovie(query: String, page: Int){
        if (query == _query.value){
            return
        }
        _query.value = query
        _page = page

    }

    fun cancelJob(message : String){
        movieRepository.cancelJob(message)
    }
}