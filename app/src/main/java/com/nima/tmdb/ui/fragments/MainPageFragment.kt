package com.nima.tmdb.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.nima.tmdb.R
import com.nima.tmdb.utils.log
import com.nima.tmdb.viewModels.MainPageViewModel
import dagger.hilt.android.AndroidEntryPoint

const val TAG : String = "MainPageFragment"

@AndroidEntryPoint
class MainPageFragment : Fragment() {
    private lateinit var sessionId : String

    private var navController: NavController? = null
    private val viewModel: MainPageViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this, MainPageViewModel.Factory(activity.application)).get(MainPageViewModel::class.java)
    }

    private fun sendRequest() {
        if (sessionId.isEmpty())
            viewModel.load()
        else viewModel.setSessionId(sessionId)

        arguments?:let{
            Log.d(TAG, "sendRequest: aslkdflksadf")
            viewModel.load()
        }
    }

    private fun getArgs() {
        arguments?.getString(R.string.sessionId.toString())?.let {
            sessionId = it
            sessionId.log(TAG)
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       /// navController = Navigation.findNavController(view)
        findNavController().navigate(R.id.action_mainPageFragment_to_movieListFragment)
    }
}