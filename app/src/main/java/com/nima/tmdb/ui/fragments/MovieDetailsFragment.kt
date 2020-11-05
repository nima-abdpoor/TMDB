package com.nima.tmdb.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.nima.tmdb.R
import com.nima.tmdb.models.Details
import com.nima.tmdb.utils.Constants
import com.nima.tmdb.viewModels.MovieDetailsViewModel
import kotlinx.android.synthetic.main.fragment_movie_details.*


@Suppress("NAME_SHADOWING")
class MovieDetailsFragment : Fragment() {
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
        val movieID = requireArguments().getInt("movieID")
        setMovieID(movieID)
        subscribeOnObservers()
    }

    private fun subscribeOnObservers() {
        viewModel.searchMovieAPI.observe(this, { details ->
            details?.let { details ->
                initViewItems(details)
            }
        })
    }

    private fun setMovieID(movieID: Int) {
        viewModel.setMovieID(movieID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }


    private fun initViewItems(details: Details) {
        var genre = "genres : "
        scrollView!!.visibility = View.VISIBLE
        title!!.text = details.title
        overview!!.text = details.overview
        rank!!.text = details.voteAverage.toString()
        for (s in details.genres!!) genre += """
 -          ${s.name}"""
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
    }
}