package com.nima.tmdb.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nima.tmdb.R
import com.nima.tmdb.requests.wrapper.ApiWrapper
import com.nima.tmdb.utils.Constants.ALL_MEDIA_TYPE
import com.nima.tmdb.utils.Constants.API_KEY
import com.nima.tmdb.utils.Constants.DAY_MEDIA_TYPE
import com.nima.tmdb.utils.Constants.DEFAULT_LANGUAGE
import com.nima.tmdb.utils.Constants.DEFAULT_PAGE
import com.nima.tmdb.utils.Constants.DEFAULT_REGION
import com.nima.tmdb.viewModels.MainPageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainPageFragment :Fragment(R.layout.fragement_main_page){

    private val TAG : String = "MainPageFragment"
    private val viewModel: MainPageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMovies()
    }

    private fun getMovies() {
        viewModel.getPopularMovies(API_KEY, DEFAULT_LANGUAGE, DEFAULT_PAGE, DEFAULT_REGION)
        viewModel.getTrendingMovies(ALL_MEDIA_TYPE, DAY_MEDIA_TYPE, API_KEY)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeOnPopularMovies()
        subscribeOnTrendingMovies()
    }

    private fun subscribeOnPopularMovies() {
        viewModel.popularMovies.observe(viewLifecycleOwner){response ->
            when(response){
                is ApiWrapper.Success -> {
                    Log.d(TAG, "subscribeOnPopularMovies: success ${response.data}")
                }
                is ApiWrapper.NetworkError ->{
                    Log.d(TAG, "subscribeOnPopularMovies: success ${response.message}")
                }
                is ApiWrapper.ApiError ->{
                    Log.d(TAG, "subscribeOnPopularMovies: success ${response.totalError}")
                }
                is ApiWrapper.UnknownError ->{
                    Log.d(TAG, "subscribeOnPopularMovies: success ${response.message}")
                }
            }
        }
    }

    private fun subscribeOnTrendingMovies() {
        viewModel.trendingMovies.observe(viewLifecycleOwner){response ->
            when(response){
                is ApiWrapper.Success -> {
                    Log.d(TAG, "subscribeOnTrendingMovies: success ${response.data}")
                }
                is ApiWrapper.NetworkError ->{
                    Log.d(TAG, "subscribeOnTrendingMovies: success ${response.message}")
                }
                is ApiWrapper.ApiError ->{
                    Log.d(TAG, "subscribeOnTrendingMovies: success ${response.totalError}")
                }
                is ApiWrapper.UnknownError ->{
                    Log.d(TAG, "subscribeOnTrendingMovies: success ${response.message}")
                }
            }
        }
    }
}