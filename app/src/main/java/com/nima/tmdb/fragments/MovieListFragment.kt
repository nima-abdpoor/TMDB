package com.nima.tmdb.fragments

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
import com.nima.tmdb.adapters.MovieRecyclerAdapter
import com.nima.tmdb.adapters.OnMovieListener
import com.nima.tmdb.models.Result
import com.nima.tmdb.utils.Constants
import com.nima.tmdb.viewModels.MovieListViewModel
import java.util.*

class MovieListFragment : Fragment(), OnMovieListener {
    private var recyclerView: RecyclerView? = null
    lateinit var mviewModel: MovieListViewModel
    private var adapter: MovieRecyclerAdapter? = null
    var navController: NavController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mviewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)
        subscribeObservers()
        initSearchView()
    }

    private fun initRecyclerView() {
        adapter = MovieRecyclerAdapter(this)
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    mviewModel!!.searchNextPage()
                }
            }
        })
    }

    private fun initSearchView() {}
    private fun subscribeObservers() {
        Log.d(TAG, "subscribeObservers: salam")
        mviewModel!!.movies.observe(this, { results: List<Result> ->
            Log.d(TAG, "subscribeObservers: $results")
            if (results != null) {
                //Testing.Test(results, TAG)
                    mviewModel!!.isMovieRetrieved = true
                    adapter!!.setResults(results)
            } else {
                mviewModel!!.isMovieRetrieved = false
            }
        })
        mviewModel!!.isRequestTimedOut.observe(this, { aBoolean ->
            if (aBoolean && !mviewModel!!.isMovieRetrieved) {
                Log.d(TAG, "onChanged: Connection Timed Out!")
                adapter!!.ShowErrorResult(context)
            }
        })
    }

    private fun searchMovieAPI(query: String, page: Int, onResume: Boolean) {
        if (onResume) loadFirstPage() else mviewModel!!.searchMovieAPI(query, page)
    }

    private fun loadFirstPage() {
        val random = Random()
        val number = random.nextInt(9)
        Log.d(TAG, "RandomNumber: $number")
        try {
            mviewModel!!.searchMovieAPI(Constants.DEFAULT_MOVIE_LIST_NAME[number], Constants.DEFAULT_PAGE)
        } catch (e: ArrayIndexOutOfBoundsException) {
            searchMovieAPI("error", Constants.DEFAULT_PAGE, false)
            Log.e(TAG, "SearchMovieAPI: searchOnResume", e)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        recyclerView = view.findViewById(R.id.movie_list)
        initRecyclerView()
        val searchView: SearchView = view.findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                adapter!!.displayLoading()
                searchMovieAPI(query, 1, false)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    override fun onMovieClick(position: Int) {
        val movieID = adapter!!.getMovieID(position)!!
        val bundle = Bundle()
        bundle.putInt("movieID", movieID)
        navController!!.navigate(R.id.action_movieListFragment_to_movieDetailsFragment, bundle)
    }

    override fun onCategoryClick(category: String?) {}
    override fun onResume() {
        super.onResume()
        searchMovieAPI("life", Constants.DEFAULT_PAGE, true)
    }

    companion object {
        private const val TAG = "MovieListFragment"
    }
}