package com.nima.tmdb.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.nima.tmdb.R
import com.nima.tmdb.viewModels.MainPageViewModel

const val TAG : String = "MainPageFragment"

class MainPageFragment : Fragment() {
    private lateinit var mainPageViewModel: MainPageViewModel

    private var navController: NavController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainPageViewModel = ViewModelProvider(this).get(MainPageViewModel::class.java)
        subscribeObservers()
    }

    private fun subscribeObservers() {
        TODO()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_page, container, false)
        val sessionId = savedInstanceState?.getString(R.string.sessionId.toString())
        Log.d(TAG, "onCreateView: $sessionId")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }
}