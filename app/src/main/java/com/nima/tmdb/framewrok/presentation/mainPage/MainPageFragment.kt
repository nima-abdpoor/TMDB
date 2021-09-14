package com.nima.tmdb.framewrok.presentation.mainPage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.nima.tmdb.R
import com.nima.tmdb.business.data.network.requests.wrapper.ApiWrapper
import com.nima.tmdb.business.domain.model.login.account.Account
import com.nima.tmdb.business.domain.model.movie.popular.PopularInfoModel
import com.nima.tmdb.business.domain.model.movie.popular.PopularModel
import com.nima.tmdb.business.domain.model.requests.FavoriteBody
import com.nima.tmdb.business.domain.model.requests.WatchlistBody
import com.nima.tmdb.business.domain.model.responses.FavoriteResponse
import com.nima.tmdb.business.domain.model.trend.TrendInfoModel
import com.nima.tmdb.business.domain.model.trend.TrendModel
import com.nima.tmdb.databinding.FragmentMainPageBinding
import com.nima.tmdb.databinding.HeaderMainMenuBinding
import com.nima.tmdb.utils.Constants.ALL_MEDIA_TYPE
import com.nima.tmdb.utils.Constants.API_KEY
import com.nima.tmdb.utils.Constants.DAY_MEDIA_TYPE
import com.nima.tmdb.utils.Constants.DEFAULT_LANGUAGE
import com.nima.tmdb.utils.Constants.DEFAULT_PAGE
import com.nima.tmdb.utils.Constants.DEFAULT_REGION
import com.nima.tmdb.utils.Constants.MOVIE_MEDIA_TYPE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainPageFragment : Fragment(R.layout.fragment_main_page), PopularMoviesAdapter.Interaction,
    TrendMoviesAdapter.Interaction {


    private val TAG: String = "MainPageFragment"
    private var accountId: Int = 0
    private var sessionId: String = ""

    @Inject
    lateinit var glide: RequestManager

    private val viewModel: MainPageViewModel by viewModels()
    private lateinit var popularMoviesAdapter: PopularMoviesAdapter
    private lateinit var trendMoviesAdapter: TrendMoviesAdapter
    private lateinit var toolbar: MaterialToolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    private var _binding: FragmentMainPageBinding? = null
    private var _headerBinding: HeaderMainMenuBinding? = null
    private val binding get() = _binding!!
    private val headerBinding get() = _headerBinding!!

    private lateinit var headerName: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionId = arguments?.getString(R.string.sessionId.toString(), "") ?: ""
        Log.d(TAG, "onCreate: $sessionId")
        getMovies()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getMovies() {
        val account = viewModel.getAccount(API_KEY, sessionId)
        subscribeOnAccountDetails(account)
        val popular =
            viewModel.getPopularMovies(API_KEY, DEFAULT_LANGUAGE, DEFAULT_PAGE, DEFAULT_REGION)
        subscribeOnPopularMovies(popular)
        val trending = viewModel.getTrendingMovies(ALL_MEDIA_TYPE, DAY_MEDIA_TYPE, API_KEY)
        subscribeOnTrendingMovies(trending)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initToolBar()
        subscribeOnViewButtons()
        subscribeOnToolbarNavigation()
    }

    private fun subscribeOnToolbarNavigation() {
        toolbar.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        val header = navigationView.inflateHeaderView(R.layout.header_main_menu)
        headerName = header.findViewById<TextView>(R.id.name_headerMain_info)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            val id = menuItem.itemId
            drawerLayout.closeDrawer(GravityCompat.START)
            when (id) {
                R.id.addToList_mainPageF_item -> {
                    val bundle = Bundle()
                    bundle.putString(R.string.accountId.toString(), accountId.toString())
                    bundle.putString(R.string.sessionId.toString(), sessionId)
                    findNavController().navigate(
                        R.id.action_mainPageFragment_to_createdListsFragment,
                        bundle
                    )
                    true
                }
                R.id.favorite_mainPageF_item -> {
                    toast("favorite")
                    true
                }
                R.id.watchList_mainPageF_item -> {
                    navigate(R.id.action_mainPageFragment_to_createdListsFragment, null)
                    true
                }
                R.id.logout_mainPageF_item -> {
                    toast("logout")
                    true
                }
                R.id.share_mainPageF_item -> {
                    toast("share")
                    true
                }
                else -> {
                    toast("NONE")
                    true
                }
            }
        }
    }

    private fun navigate(actionId: Int, bundle: Bundle?) {
        bundle?.let {
            findNavController().navigate(actionId, bundle)
        } ?: run {
            findNavController().navigate(actionId, bundle)
        }
    }

    private fun toast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun initToolBar() {
        toolbar = binding.toolbarMainPageFTopAppbar
        drawerLayout = binding.root
        navigationView = binding.navigationViewMainPageFNavigation
    }

    private fun subscribeOnWatchlistMovies(result: StateFlow<ApiWrapper<FavoriteResponse>>) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                result.collect { response ->
                    when (response) {
                        is ApiWrapper.Success -> {
                            Log.d(TAG, "subscribeOnWatchlistMovies: success ${response.data}")
                            response.data?.let {
                                if (it.success)
                                    Toast.makeText(
                                        requireContext(),
                                        "added to your watchlist",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                else {
                                    Toast.makeText(
                                        requireContext(),
                                        "oops! something wrong happened!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                        is ApiWrapper.NetworkError -> {
                            Log.d(TAG, "subscribeOnWatchlistMovies: net ${response.message}")
                            Toast.makeText(
                                requireContext(),
                                "check your connection!",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                        is ApiWrapper.ApiError -> {
                            Log.d(TAG, "subscribeOnWatchlistMovies: api ${response.totalError}")
                            Toast.makeText(
                                requireContext(),
                                "oops! something wrong happened!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is ApiWrapper.UnknownError -> {
                            Log.d(TAG, "subscribeOnWatchlistMovies: unknown ${response.message}")
                            Toast.makeText(
                                requireContext(),
                                "oops! something wrong happened!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is ApiWrapper.Loading -> {
                            Log.d(TAG, "subscribeOnWatchlistMovies: Loading ")
                        }
                    }
                }
            }
        }
    }

    private fun subscribeOnFavoriteMovies(result: StateFlow<ApiWrapper<FavoriteResponse>>) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                result.collect { response ->
                    when (response) {
                        is ApiWrapper.Success -> {
                            Log.d(TAG, "subscribeOnFavoriteMovies: success ${response.data}")
                            response.data?.let {
                                if (it.success)
                                    Toast.makeText(
                                        requireContext(),
                                        "added to your favorite list",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                else {
                                    Toast.makeText(
                                        requireContext(),
                                        "oops! something wrong happened!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                        is ApiWrapper.NetworkError -> {
                            Log.d(TAG, "subscribeOnFavoriteMovies: net ${response.message}")
                            Toast.makeText(
                                requireContext(),
                                "check your connection!",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                        is ApiWrapper.ApiError -> {
                            Log.d(TAG, "subscribeOnFavoriteMovies: api ${response.totalError}")
                            Toast.makeText(
                                requireContext(),
                                "oops! something wrong happened!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is ApiWrapper.UnknownError -> {
                            Log.d(TAG, "subscribeOnFavoriteMovies: unknown ${response.message}")
                            Toast.makeText(
                                requireContext(),
                                "oops! something wrong happened!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is ApiWrapper.Loading -> Log.d(TAG, "subscribeOnFavoriteMovies: loading")
                    }
                }
            }
        }
    }

    private fun subscribeOnViewButtons() {
        binding.btnMainPageFSearchButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainPageFragment_to_movieListFragment)
        }
    }

    private fun initRecyclerView() {
        binding.rvMainPageFPopularItems.apply {
            popularMoviesAdapter =
                PopularMoviesAdapter(this@MainPageFragment, glide, requireContext())
            adapter = popularMoviesAdapter
        }
        binding.rvMainPageFTrendingItems.apply {
            trendMoviesAdapter = TrendMoviesAdapter(this@MainPageFragment, glide, requireContext())
            adapter = trendMoviesAdapter
        }
    }


    private fun subscribeOnPopularMovies(popular: StateFlow<ApiWrapper<PopularInfoModel>>) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                popular.collect { response ->
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
                        is ApiWrapper.Loading -> Log.d(TAG, "subscribeOnPopularMovies: loading")
                    }
                }
            }
        }
    }

    private fun subscribeOnAccountDetails(account: StateFlow<ApiWrapper<Account>>) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                account.collect { response ->
                    when (response) {
                        is ApiWrapper.Success -> {
                            Log.d(TAG, "subscribeOnAccountDetails: success ${response.data}")
                            setAccountInfo(response.data)
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
                        is ApiWrapper.Loading -> Log.d(TAG, "subscribeOnAccountDetails: Loading")
                    }
                }
            }
        }
    }

    private fun setAccountInfo(account: Account?) {
        account?.let {
            Log.d(TAG, "setAccountInfo: $it")
            headerName.text = it.username
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

    private fun subscribeOnTrendingMovies(trending: StateFlow<ApiWrapper<TrendInfoModel>>) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                trending.collect { response ->
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
                        is ApiWrapper.Loading -> Log.d(TAG, "subscribeOnTrendingMovies: Loading")
                    }
                }
            }
        }
    }

    private fun markAsFavorite(id: Int, favorite: Boolean) {
        val favoriteBody = FavoriteBody(MOVIE_MEDIA_TYPE, id, favorite)
        Log.d(
            TAG, "subscribeOnFavoriteMovies:favoriteBody :" +
                    "$favoriteBody " +
                    "id : $accountId" +
                    ""
        )
        val result = viewModel.markAsFavorite(favoriteBody, accountId, API_KEY, sessionId)
        subscribeOnFavoriteMovies(result)
    }

    private fun addToWatchList(id: Int, watchlist: Boolean) {
        val watchlistBody = WatchlistBody(MOVIE_MEDIA_TYPE, id, watchlist)
        Log.d(
            TAG, "subscribeOnFavoriteMovies:favoriteBody :" +
                    "$watchlistBody " +
                    "id : $accountId" +
                    ""
        )
        val result = viewModel.addToWatchlist(watchlistBody, accountId, API_KEY, sessionId)
        subscribeOnWatchlistMovies(result)
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
        item.id?.let { markAsFavorite(it, true) }
    }

    override fun addToWatchList(position: Int, item: PopularModel) {
        item.id?.let { addToWatchList(it, true) }
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
        item.id?.let { markAsFavorite(it, true) }
    }

    override fun addToWatchList(position: Int, item: TrendModel) {
        item.id?.let { addToWatchList(it, true) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}