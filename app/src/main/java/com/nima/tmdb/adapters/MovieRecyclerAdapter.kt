package com.nima.tmdb.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nima.tmdb.R
import com.nima.tmdb.uiHelpers.DrawGlide
import com.nima.tmdb.models.Result
import com.nima.tmdb.utils.Constants
import java.util.*

class MovieRecyclerAdapter(private val onMovieListener: OnMovieListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val MOVIE_TYPE = 1
    private val LOADING_TYPE = 2
    private val ERROR_TYPE = 3
    private var error = false
    var drawGlide: DrawGlide = DrawGlide()
    private var oldList: MutableList<Result> = ArrayList()
    private var results: List<Result>? = null
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        Log.d(TAG, "onCreateViewHolder: $i")
        val view: View
        return when (i) {
            LOADING_TYPE -> {
                view = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item_loading, viewGroup, false)
                LoadingViewHolder(view)
            }
            MOVIE_TYPE -> {
                view = LayoutInflater.from(viewGroup.context).inflate(R.layout.recycler_view_list_item, viewGroup, false)
                MovieViewHolder(view, onMovieListener)
            }
            ERROR_TYPE -> {
                view = LayoutInflater.from(viewGroup.context).inflate(R.layout.error_in_list_movies, viewGroup, false)
                ErrorViewHolder(view)
            }
            else -> {
                view = LayoutInflater.from(viewGroup.context).inflate(R.layout.error_in_list_movies, viewGroup, false)
                ErrorViewHolder(view)
            }
        }
    }

    fun ShowErrorResult(context: Context?) {
        Toast.makeText(context, "check Internet Connection", Toast.LENGTH_SHORT).show()
        error = true
        notifyDataSetChanged()
        Log.d(TAG, "ShowErrorResult: err")
    }

    fun setResults(results: List<Result>?) {
        if (results != null) {
            error = false
            this.results = results
            //UpdateResulsts(results);
            notifyDataSetChanged()
        }
    }

    private fun UpdateResulsts(results: MutableList<Result>) {
        val diffUtils = MyDiffUtils(oldList, results)
        val diffResult = DiffUtil.calculateDiff(diffUtils)
        results.clear()
        oldList.addAll(results)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        Log.d(TAG, "onBindViewHolder: ")
        val itemViewType = getItemViewType(i)
        if (itemViewType == MOVIE_TYPE) {
            drawGlide.draw(
                    viewHolder.itemView.context,
                    Constants.DEFAULT_IMAGE_REQUEST,
                    Constants.IMAGE_BASE_URL + results!![i].posterPath,
                    (viewHolder as MovieViewHolder).image)
            viewHolder.title.text = results!![i].title
            viewHolder.description.text = getOverview(results!![i])
            viewHolder.releaseDate.text = results!![i].release_date.toString()
        }
        if (itemViewType == ERROR_TYPE) {
            (viewHolder as ErrorViewHolder).error.text = "Check Your Internet Connection!"
            viewHolder.button.setOnClickListener { }
        }
    }

    fun getMovieID(position: Int): Int? {
        if (results != null) {
            if (results!!.isNotEmpty()) {
                return results!![position].id
            }
        }
        return null
    }

    private fun getOverview(result: Result): String {
        try {
            return result.overview?.substring(0, 20) + "..."
        } catch (s: StringIndexOutOfBoundsException) {
            Log.d(TAG, "getOverview: $s")
        }
        return "..."
    }

    override fun getItemViewType(position: Int): Int {
        return if (error) {
            ERROR_TYPE
        } else if (results!![position].title == "LOADING...") {
            error = false
            LOADING_TYPE
        } else if (position == results!!.size - 1 && position != 0) {
            error = false
            LOADING_TYPE
        } else MOVIE_TYPE
    }

    fun displayLoading() {
        if (!isLoading) {
            val result = Result()
            result.title = "LOADING..."
            val loadingResults: MutableList<Result> = ArrayList()
            loadingResults.add(result)
            results = loadingResults
            notifyDataSetChanged()
        }
    }

    private val isLoading: Boolean
        private get() {
            if (results != null) {
                if (results!!.size > 0) {
                    if (results!![results!!.size - 1].title == "LOADING ...") {
                        return true
                    }
                }
            }
            return false
        }

    override fun getItemCount(): Int {
        return if (results != null) {
            results!!.size
        } else 0
    }

    companion object {
        private const val TAG = "RecyclerView"
    }

}