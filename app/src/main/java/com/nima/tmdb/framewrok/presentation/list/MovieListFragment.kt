package com.nima.tmdb.framewrok.presentation.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nima.tmdb.R
import com.nima.tmdb.adapters.MovieListAdapter
import com.nima.tmdb.databinding.FragmentMovieListBinding
import com.nima.tmdb.business.domain.model.Example
import com.nima.tmdb.business.domain.model.Result
import com.nima.tmdb.requests.wrapper.ApiWrapper
import com.nima.tmdb.ui.uiHelpers.ErrorAdapter
import com.nima.tmdb.utils.Constants
import com.nima.tmdb.utils.Constants.API_KEY
import com.nima.tmdb.utils.Constants.DEFAULT_ADULT
import com.nima.tmdb.utils.Constants.DEFAULT_LANGUAGE
import com.nima.tmdb.utils.TopSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@Suppress("NAME_SHADOWING")
@AndroidEntryPoint
class MovieListFragment : Fragment(R.layout.fragment_movie_list), MovieListAdapter.Interaction,
    ErrorAdapter.TryAgain {

    private val TAG = "MovieListFragment"
    private var firstTime = true

    private val viewModel: MovieListViewModel by viewModels()

    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var errorAdapter: ErrorAdapter
    private lateinit var searchView: SearchView

    private var _query: String? = null
    private var recyclerView: RecyclerView? = null

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewItems()
        initRecyclerView()
        subscribeOnSearchView()
        subscribeOnMovieListObserver()
        loadFirstPage()
    }

    private fun initViewItems() {
        recyclerView = view?.findViewById(R.id.recycler_view)
        searchView = view?.findViewById(R.id.search_view)!!
    }

    private fun subscribeOnSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                _query = query
                searchMovieAPI(query, 1, false)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun subscribeOnMovieListObserver() {
        viewModel.movieList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiWrapper.Success -> handleSuccessData(response.data)
                is ApiWrapper.ApiError -> {
                    handleErrorData(response.message)
                    Log.d(TAG, "subscribeOnMovieListObserver: ${response.totalError}")
                }
                is ApiWrapper.NetworkError -> {
                    handleErrorData(response.message)
                    Log.d(TAG, "subscribeOnMovieListObserver: ${response.message}")
                }
                is ApiWrapper.UnknownError -> {
                    handleErrorData(response.message)
                    Log.d(TAG, "subscribeOnMovieListObserver: ${response.message}")
                }
            }
        }
    }

    private fun handleLoadingData() {
        Log.d(TAG, "handleLoadingData: isLoading...")
    }

    private fun handleErrorData(message: String?) {
        binding.recyclerView.apply {
            errorAdapter = ErrorAdapter(this@MovieListFragment)
            layoutManager = LinearLayoutManager(activity)
            adapter = errorAdapter
        }
        message?.let { error ->

            showToastMessage(error)
        }
    }

    private fun showToastMessage(error: String) {
        Log.d(TAG, "handleErrorData: $error")
        Toast.makeText(context, "Cant Connect To The Server!!", Toast.LENGTH_SHORT).show()
    }

    private fun handleSuccessData(data: Example?) {
        initRecyclerView()
        data?.let { example ->
            example.results?.let { movieListAdapter.submitList(it) }
        }
    }

    private fun initRecyclerView() {
        val topSpacingItemDecoration = TopSpacingItemDecoration(padding = Constants.PADDING)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(topSpacingItemDecoration)
            movieListAdapter = MovieListAdapter(this@MovieListFragment)
            adapter = movieListAdapter
        }
    }

    private fun searchMovieAPI(query: String = "", page: Int = 1, onResume: Boolean) {
        if (onResume) loadFirstPage() else {
            viewModel.setMovie(
                API_KEY,
                DEFAULT_LANGUAGE,
                query,
                page,
                DEFAULT_ADULT
            )
        }
    }

    private fun loadFirstPage() {
        val random = Random()
        val number = random.nextInt(9)
        Log.d(TAG, "RandomNumber: $number")
        viewModel.setMovie(
            API_KEY,
            DEFAULT_LANGUAGE,
            Constants.DEFAULT_MOVIE_LIST_NAME[number],
            Constants.DEFAULT_PAGE,
            DEFAULT_ADULT
        )
    }

    override fun onItemSelected(position: Int, item: Result) {
        item.id?.let { id ->
            val bundle = Bundle()
            bundle.putInt("movieID", id)
            findNavController().navigate(
                R.id.action_movieListFragment_to_movieDetailsFragment,
                bundle
            )
            Log.d(TAG, "onItemSelected: $position")
        }
    }

    override fun onClick() {
        _query?.let {
            searchMovieAPI(it, 1, false)
            Log.d(TAG, "onClick: searching for $_query")
        } ?: let {
            loadFirstPage()
            Log.d(TAG, "onClick: loading first page")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}