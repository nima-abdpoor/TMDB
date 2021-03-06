package com.nima.tmdb.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.nima.tmdb.R
import com.nima.tmdb.models.Details
import com.nima.tmdb.requests.wrapper.ApiWrapper
import com.nima.tmdb.utils.Constants
import com.nima.tmdb.utils.Constants.API_KEY
import com.nima.tmdb.utils.Constants.DEFAULT_LANGUAGE
import com.nima.tmdb.viewModels.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_details.*


@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {
    //UI
    var title: TextView? = null
    var overview: TextView? = null
    var rank: TextView? = null
    var genres: TextView? = null
    var scrollView: ScrollView? = null
    private lateinit var viewModel: MovieDetailsViewModel


    private val TAG = "MovieDetailsActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)
        val movieId = requireArguments().getInt("movieID")
        setMovieID(movieId, DEFAULT_LANGUAGE)
    }

    private fun subscribeOnObservers() {
        viewModel.movieDetails.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiWrapper.Success -> {
                    response.data?.let {
                        initViewItems(it)
                    }
                }
                is ApiWrapper.ApiError -> {
                    Log.d(TAG, "subscribeOnObservers api: ${response.totalError}")
                }
                is ApiWrapper.NetworkError -> {
                    Log.d(TAG, "subscribeOnObservers net: ${response.totalError}")
                }
                is ApiWrapper.UnknownError -> {
                    Log.d(TAG, "subscribeOnObservers unKnown: ${response.totalError}")
                }
            }
        }
    }

    private fun setMovieID(movieId: Int,language: String) {
        viewModel.setMovieID(movieId, API_KEY, language)
    }


    private fun initViewItems(details: Details) {
        card_view.animation =
            AnimationUtils.loadAnimation(context, R.anim.card_view_anim_one)
        var genre = "genres : "
        scrollView!!.visibility = View.VISIBLE
        title!!.text = details.title
        overview!!.text = details.overview
        rank!!.text = details.voteAverage.toString()
        for (s in details.genres!!) genre += """-${s.name}"""
        genres!!.text = genre
        context?.let {
            Glide.with(it)
                .load(Constants.IMAGE_BASE_URL + details.backdropPath)
                .into(movie_image_detail)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title = view.findViewById(R.id.movie_title_detail)
        overview = view.findViewById(R.id.overview_title)
        rank = view.findViewById(R.id.movie_vote)
        scrollView = view.findViewById(R.id.parent)
        genres = view.findViewById(R.id.genres)
        subscribeOnObservers()
    }
}