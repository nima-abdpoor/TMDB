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
    private lateinit var sessionId : String

    private var navController: NavController? = null
    private val viewModel: MainPageViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this, MainPageViewModel.Factory(activity.application)).get(MainPageViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getArgs()
        Log.d(TAG, "getArgs: lsakdfjlksadl")
        subscribeObservers()
    }

    private fun getArgs() {
        arguments?.getString(R.string.sessionId.toString())?.let {
            sessionId = it
            viewModel.setSessionId(sessionId)
        }
        if (sessionId.isEmpty()){
            viewModel.load()
        }
        arguments?:let{
            viewModel.load()
        }
    }

    private fun subscribeObservers() {
        viewModel.query.observe(this){account ->
            account?.let {
                Log.d(TAG, "subscribeObservers: $it")
                //viewModel.save(it)
            }
        }
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }
}