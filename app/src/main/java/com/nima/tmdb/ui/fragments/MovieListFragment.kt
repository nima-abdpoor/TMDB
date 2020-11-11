package com.nima.tmdb.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nima.tmdb.R
import com.nima.tmdb.adapters.MovieListAdapter
import com.nima.tmdb.models.Example
import com.nima.tmdb.models.Result
import com.nima.tmdb.ui.uiHelpers.ErrorAdapter
import com.nima.tmdb.utils.Constants
import com.nima.tmdb.utils.Status
import com.nima.tmdb.utils.TopSpacingItemDecoration
import com.nima.tmdb.viewModels.MovieListViewModel
import kotlinx.android.synthetic.main.fragment_movie_list.*
import java.util.*

@Suppress("NAME_SHADOWING")
class MovieListFragment : Fragment(), MovieListAdapter.Interaction,ErrorAdapter.TryAgain {
    private var _query : String? =null
    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var errorAdapter: ErrorAdapter
    private var recyclerView: RecyclerView? = null
    lateinit var mviewModel: MovieListViewModel
    var navController: NavController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mviewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)
        subscribeObservers()
    }

    private fun subscribeObservers() {
        mviewModel.searchMovieAPI.observe(this, { networkResource->
            when(networkResource.status){
                Status.SUCCESS -> handleSuccessData(networkResource.data)
                Status.ERROR -> handleErrorData(networkResource.msg)
                Status.LOADING -> handleLoadingData()
            }

        })
    }

    private fun handleLoadingData() {
        Log.d(TAG, "handleLoadingData: isLoading...")
    }

    private fun handleErrorData(message: String?) {

        message?.let {error->
            recycler_view.apply {
                errorAdapter = ErrorAdapter(this@MovieListFragment)
                layoutManager = LinearLayoutManager(activity)
                adapter = errorAdapter
            }
            Log.d(TAG, "handleErrorData: $error")
        }
    }

    private fun handleSuccessData(data: Example?) {
        data?.let {example->
            example.results?.let { movieListAdapter.submitList(it) }
        }
    }

    private fun initRecyclerView() {
        val topSpacingItemDecoration =  TopSpacingItemDecoration(padding = Constants.PADDING)
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(topSpacingItemDecoration)
            movieListAdapter = MovieListAdapter(this@MovieListFragment)
            adapter = movieListAdapter
        }
    }

    private fun searchMovieAPI(query: String ="" , page: Int = 1, onResume: Boolean) {
        if (onResume) loadFirstPage() else mviewModel.setMovie(query, page)
    }

    private fun loadFirstPage() {
        val random = Random()
        val number = random.nextInt(9)
        Log.d(TAG, "RandomNumber: $number")
        try {
            mviewModel.setMovie(Constants.DEFAULT_MOVIE_LIST_NAME[number], Constants.DEFAULT_PAGE)
        } catch (e: ArrayIndexOutOfBoundsException) {
            searchMovieAPI("error", Constants.DEFAULT_PAGE, false)
            Log.e(TAG, "SearchMovieAPI: searchOnResume", e)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        recyclerView = view.findViewById(R.id.recycler_view)
        initRecyclerView()
        val searchView: SearchView = view.findViewById(R.id.search_view)
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


    override fun onResume() {
        super.onResume()
        searchMovieAPI(onResume =  true)
    }

    companion object {
        private const val TAG = "MovieListFragment"
    }

    override fun onDestroy() {
        super.onDestroy()
        mviewModel.cancelJob("context destroyed!")
    }

    override fun onItemSelected(position: Int, item: Result) {
        item.id?.let {id ->
            val bundle = Bundle()
            bundle.putInt("movieID", id)
            navController!!.navigate(R.id.action_movieListFragment_to_movieDetailsFragment, bundle)
            Log.d(TAG, "onItemSelected: $position")
        }
    }

    override fun onClick() {
        _query?.let {
            searchMovieAPI(it, 1, false)
        }?: loadFirstPage()

    }
}