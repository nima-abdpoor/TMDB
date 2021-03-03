package com.nima.tmdb.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.nima.tmdb.R
import com.nima.tmdb.adapters.PopularMoviesAdapter
import com.nima.tmdb.adapters.TrendMoviesAdapter
import com.nima.tmdb.models.movie.popular.PopularInfoModel
import com.nima.tmdb.models.movie.popular.PopularModel
import com.nima.tmdb.models.trend.TrendInfoModel
import com.nima.tmdb.models.trend.TrendModel
import com.nima.tmdb.requests.wrapper.ApiWrapper
import com.nima.tmdb.utils.Constants.ALL_MEDIA_TYPE
import com.nima.tmdb.utils.Constants.API_KEY
import com.nima.tmdb.utils.Constants.DAY_MEDIA_TYPE
import com.nima.tmdb.utils.Constants.DEFAULT_LANGUAGE
import com.nima.tmdb.utils.Constants.DEFAULT_PAGE
import com.nima.tmdb.utils.Constants.DEFAULT_REGION
import com.nima.tmdb.viewModels.MainPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main_page.*
import kotlinx.android.synthetic.main.fragment_movie_list.*
import javax.inject.Inject

@AndroidEntryPoint
class MainPageFragment : Fragment(R.layout.fragment_main_page), PopularMoviesAdapter.Interaction,
    TrendMoviesAdapter.Interaction {


    private val TAG: String = "MainPageFragment"

    @Inject
    lateinit var glide: RequestManager

    private val viewModel: MainPageViewModel by viewModels()
    private lateinit var popularMoviesAdapter: PopularMoviesAdapter
    private lateinit var trendMoviesAdapter: TrendMoviesAdapter

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
        initRecyclerView()
        subscribeOnPopularMovies()
        subscribeOnTrendingMovies()
    }

    private fun initRecyclerView() {
        rv_mainPageF_popularItems.apply {
            popularMoviesAdapter = PopularMoviesAdapter(this@MainPageFragment, glide)
            adapter = popularMoviesAdapter
        }
        rv_mainPageF_trendingItems.apply {
            trendMoviesAdapter = TrendMoviesAdapter(this@MainPageFragment, glide)
            adapter = trendMoviesAdapter
        }
    }


    private fun subscribeOnPopularMovies() {
        viewModel.popularMovies.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiWrapper.Success -> {
                    Log.d(TAG, "subscribeOnPopularMovies: success ${response.data}")
                    submitPopularMoviesData(response.data)
                }
                is ApiWrapper.NetworkError -> {
                    Log.d(TAG, "subscribeOnPopularMovies: success ${response.message}")
                }
                is ApiWrapper.ApiError -> {
                    Log.d(TAG, "subscribeOnPopularMovies: success ${response.totalError}")
                }
                is ApiWrapper.UnknownError -> {
                    Log.d(TAG, "subscribeOnPopularMovies: success ${response.message}")
                }
            }
        }
    }

    private fun submitPopularMoviesData(data: PopularInfoModel?) {
        data?.let {
            Log.d(TAG, "submitPopularMoviesData: ${data.results.toString()}")
            data.results?.let { movies -> popularMoviesAdapter.submitList(movies) }
        }
    }

    private fun submitTrendMoviesData(data: TrendInfoModel?) {
        data?.let {
            Log.d(TAG, "submitTrendMoviesData: ${data.results.toString()}")
            data.results?.let { movies -> trendMoviesAdapter.submitList(movies) }
        }
    }

    private fun subscribeOnTrendingMovies() {
        viewModel.trendingMovies.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiWrapper.Success -> {
                    Log.d(TAG, "subscribeOnTrendingMovies: success ${response.data}")
                    submitTrendMoviesData(response.data)
                }
                is ApiWrapper.NetworkError -> {
                    Log.d(TAG, "subscribeOnTrendingMovies: net ${response.message}")
                }
                is ApiWrapper.ApiError -> {
                    Log.d(TAG, "subscribeOnTrendingMovies: api ${response.message}")
                }
                is ApiWrapper.UnknownError -> {
                    Log.d(TAG, "subscribeOnTrendingMovies: un ${response.message}")
                }
            }
        }
    }

    override fun onPopularItemSelected(position: Int, item: PopularModel) {
        Log.d(TAG, "onTrendItemSelected: ${item.id}")
        item.id?.let { id ->
            val bundle = Bundle()
            bundle.putInt("movieID", id)
            findNavController().navigate(
                R.id.action_mainPageFragment_to_movieDetailsFragment,
                bundle
            )
        }
    }

    override fun onTrendItemSelected(position: Int, item: TrendModel) {
        Log.d(TAG, "onTrendItemSelected: ${item.id}")
        item.id?.let { id ->
            val bundle = Bundle()
            bundle.putInt("movieID", id)
            findNavController().navigate(
                R.id.action_mainPageFragment_to_movieDetailsFragment,
                bundle
            )
        }
    }
}