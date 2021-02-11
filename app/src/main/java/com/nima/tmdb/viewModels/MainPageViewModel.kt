package com.nima.tmdb.viewModels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nima.tmdb.models.movie.popular.PopularInfoModel
import com.nima.tmdb.models.trend.TrendInfoModel
import com.nima.tmdb.repositories.MainPageRepository
import com.nima.tmdb.requests.wrapper.ApiWrapper
import kotlinx.coroutines.launch

class MainPageViewModel @ViewModelInject constructor(
    private val repository: MainPageRepository
) : ViewModel() {

    private val _popularMovies = MutableLiveData<ApiWrapper<PopularInfoModel>>()
    val popularMovies: LiveData<ApiWrapper<PopularInfoModel>>
        get() = _popularMovies

    private val _trendingMovies = MutableLiveData<ApiWrapper<TrendInfoModel>>()
    val trendingMovies: LiveData<ApiWrapper<TrendInfoModel>>
        get() = _trendingMovies


    fun getPopularMovies(apiKey: String,language: String,page: Int,region : String) =
        viewModelScope.launch {
            _popularMovies.value = repository.getPopularMovies(apiKey,language,page,region)
        }

    fun getTrendingMovies(mediaType : String , timeWindow : String , apiKey: String) =
        viewModelScope.launch {
            _trendingMovies.value = repository.getTrending(mediaType,timeWindow,apiKey)
        }
}