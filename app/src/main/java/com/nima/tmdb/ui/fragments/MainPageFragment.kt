package com.nima.tmdb.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.nima.tmdb.R
import com.nima.tmdb.adapters.PopularMoviesAdapter
import com.nima.tmdb.adapters.TrendMoviesAdapter
import com.nima.tmdb.models.movie.popular.PopularInfoModel
import com.nima.tmdb.models.movie.popular.PopularModel
import com.nima.tmdb.models.requests.FavoriteBody
import com.nima.tmdb.models.requests.WatchlistBody
import com.nima.tmdb.models.trend.TrendInfoModel
import com.nima.tmdb.models.trend.TrendModel
import com.nima.tmdb.requests.wrapper.ApiWrapper
import com.nima.tmdb.utils.Constants.ALL_MEDIA_TYPE
import com.nima.tmdb.utils.Constants.API_KEY
import com.nima.tmdb.utils.Constants.DAY_MEDIA_TYPE
import com.nima.tmdb.utils.Constants.DEFAULT_LANGUAGE
import com.nima.tmdb.utils.Constants.DEFAULT_PAGE
import com.nima.tmdb.utils.Constants.DEFAULT_REGION
import com.nima.tmdb.utils.Constants.MOVIE_MEDIA_TYPE
import com.nima.tmdb.viewModels.MainPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main_page.*
import kotlinx.android.synthetic.main.fragment_movie_list.*
import javax.inject.Inject

@AndroidEntryPoint
class MainPageFragment : Fragment(R.layout.fragment_main_page), PopularMoviesAdapter.Interaction,
    TrendMoviesAdapter.Interaction {


    private val TAG: String = "MainPageFragment"
    private var accountId :Int = 0
    private var sessionId :String = ""

    @Inject
    lateinit var glide: RequestManager

    private val viewModel: MainPageViewModel by viewModels()
    private lateinit var popularMoviesAdapter: PopularMoviesAdapter
    private lateinit var trendMoviesAdapter: TrendMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionId = arguments?.getString(R.string.sessionId.toString(), "") ?: ""
        Log.d(TAG, "onCreate: $sessionId")
        getMovies()
    }

    private fun getMovies() {
        viewModel.getPopularMovies(API_KEY, DEFAULT_LANGUAGE, DEFAULT_PAGE, DEFAULT_REGION)
        viewModel.getTrendingMovies(ALL_MEDIA_TYPE, DAY_MEDIA_TYPE, API_KEY)
        viewModel.getAccount(API_KEY,sessionId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        subscribeOnViewButtons()
        subscribeOnPopularMovies()
        subscribeOnTrendingMovies()
        subscribeOnAccountDetails()
        subscribeOnFavoriteMovies()
        subscribeOnWatchlistMovies()
    }

    private fun subscribeOnWatchlistMovies() {
        viewModel.watchlistResponse.observe(viewLifecycleOwner){response ->
            when (response) {
                is ApiWrapper.Success -> {
                    Log.d(TAG, "subscribeOnWatchlistMovies: success ${response.data}")
                    response.data?.let {
                        if (it.success)
                            Toast.makeText(requireContext(),"added to your watchlist",Toast.LENGTH_SHORT).show()
                        else{
                            Toast.makeText(requireContext(),"oops! something wrong happened!",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                is ApiWrapper.NetworkError -> {
                    Log.d(TAG, "subscribeOnWatchlistMovies: net ${response.message}")
                    Toast.makeText(requireContext(),"check your connection!",Toast.LENGTH_SHORT).show()
                }
                is ApiWrapper.ApiError -> {
                    Log.d(TAG, "subscribeOnWatchlistMovies: api ${response.totalError}")
                    Toast.makeText(requireContext(),"oops! something wrong happened!",Toast.LENGTH_SHORT).show()
                }
                is ApiWrapper.UnknownError -> {
                    Log.d(TAG, "subscribeOnWatchlistMovies: unknown ${response.message}")
                    Toast.makeText(requireContext(),"oops! something wrong happened!",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun subscribeOnFavoriteMovies() {
        viewModel.favoriteResponse.observe(viewLifecycleOwner){response ->
            when (response) {
                is ApiWrapper.Success -> {
                    Log.d(TAG, "subscribeOnFavoriteMovies: success ${response.data}")
                    response.data?.let {
                        if (it.success)
                            Toast.makeText(requireContext(),"added to your favorite list",Toast.LENGTH_SHORT).show()
                        else{
                            Toast.makeText(requireContext(),"oops! something wrong happened!",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                is ApiWrapper.NetworkError -> {
                    Log.d(TAG, "subscribeOnFavoriteMovies: net ${response.message}")
                    Toast.makeText(requireContext(),"check your connection!",Toast.LENGTH_SHORT).show()
                }
                is ApiWrapper.ApiError -> {
                    Log.d(TAG, "subscribeOnFavoriteMovies: api ${response.totalError}")
                    Toast.makeText(requireContext(),"oops! something wrong happened!",Toast.LENGTH_SHORT).show()
                }
                is ApiWrapper.UnknownError -> {
                    Log.d(TAG, "subscribeOnFavoriteMovies: unknown ${response.message}")
                    Toast.makeText(requireContext(),"oops! something wrong happened!",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun subscribeOnViewButtons() {
        btn_mainPageF_searchButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainPageFragment_to_movieListFragment)
        }
    }

    private fun initRecyclerView() {
        rv_mainPageF_popularItems.apply {
            popularMoviesAdapter = PopularMoviesAdapter(this@MainPageFragment, glide,requireContext())
            adapter = popularMoviesAdapter
        }
        rv_mainPageF_trendingItems.apply {
            trendMoviesAdapter = TrendMoviesAdapter(this@MainPageFragment, glide,requireContext())
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

    private fun subscribeOnAccountDetails() {
        viewModel.accountDetail.observe(viewLifecycleOwner){response ->
            when(response){
                is ApiWrapper.Success -> {
                    Log.d(TAG, "subscribeOnAccountDetails: success ${response.data}")
                    accountId = response.data?.id ?: 0
                }
                is ApiWrapper.NetworkError -> {
                    Log.d(TAG, "subscribeOnAccountDetails: api ${response.message}")
                }
                is ApiWrapper.UnknownError -> {
                    Log.d(TAG, "subscribeOnAccountDetails: api ${response.message}")
                }
                is ApiWrapper.ApiError -> {
                    Log.d(TAG, "subscribeOnAccountDetails: api ${response.totalError}")
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
    private fun markAsFavorite(id: Int,favorite: Boolean) {
        val favoriteBody = FavoriteBody(MOVIE_MEDIA_TYPE,id,favorite)
        Log.d(TAG, "subscribeOnFavoriteMovies:favoriteBody :" +
                "$favoriteBody " +
                "id : $accountId" +
                "")
        viewModel.markAsFavorite(favoriteBody,accountId, API_KEY,sessionId)
    }

    private fun addToWatchList(id: Int, watchlist: Boolean) {
        val watchlistBody = WatchlistBody(MOVIE_MEDIA_TYPE,id,watchlist)
        Log.d(TAG, "subscribeOnFavoriteMovies:favoriteBody :" +
                "$watchlistBody " +
                "id : $accountId" +
                "")
        viewModel.addToWatchlist(watchlistBody,accountId, API_KEY,sessionId)
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

    override fun addToList(position: Int, item: PopularModel) {
        TODO("Not yet implemented")
    }

    override fun addToFavorite(position: Int, item: PopularModel) {
        item.id?.let { markAsFavorite(it,true) }
    }

    override fun addToWatchList(position: Int, item: PopularModel) {
        item.id?.let { addToWatchList(it,true) }
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

    override fun addToList(position: Int, item: TrendModel) {
        TODO("Not yet implemented")
    }

    override fun addToFavorite(position: Int, item: TrendModel) {
        Log.d(TAG, "addToFavorite: $item")
        item.id?.let { markAsFavorite(it,true) }
    }

    override fun addToWatchList(position: Int, item: TrendModel) {
        item.id?.let { addToWatchList(it,true) }
    }
}