package com.nima.tmdb.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.RequestManager
import com.nima.tmdb.R
import com.nima.tmdb.databinding.FragmentCreatedListsBinding
import com.nima.tmdb.requests.wrapper.ApiWrapper
import com.nima.tmdb.utils.Constants.API_KEY
import com.nima.tmdb.viewModels.CreatedListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreatedListsFragment : Fragment(R.layout.fragment_created_lists) {

    @Inject
    lateinit var glide: RequestManager

    private val TAG: String = "MainPageFragment"
    private var accountId: String = ""
    private var sessionId: String = ""

    private val viewModel: CreatedListViewModel by viewModels()

    private var _binding: FragmentCreatedListsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionId = arguments?.getString(R.string.sessionId.toString(), "") ?: ""
        accountId = arguments?.getString(R.string.accountId.toString(), "") ?: ""
        Log.d(TAG, "onCreate: sessionId: $sessionId || accountId: $accountId")
        getCreatedLists()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeOnCreatedLists()
    }

    private fun subscribeOnCreatedLists() {
        viewModel.createdLists.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiWrapper.ApiError -> {
                    Log.d(TAG, "subscribeOnCreatedLists: net ${response.toString()}")
                    Toast.makeText(requireContext(), "check your connection!", Toast.LENGTH_SHORT)
                        .show()
                }
                is ApiWrapper.NetworkError -> {
                    Log.d(TAG, "subscribeOnCreatedLists: NetworkError ${response.message}")
                    Toast.makeText(requireContext(), "check your connection!", Toast.LENGTH_SHORT)
                        .show()
                }
                is ApiWrapper.Success -> {
                    Log.d(TAG, "subscribeOnCreatedLists: Success ${response.message}")
                    handleCreatedListSuccess()
                }
                is ApiWrapper.UnknownError -> {
                    Log.d(TAG, "subscribeOnCreatedLists: UnknownError ${response.message}")
                    Toast.makeText(requireContext(), "oops! something wrong happened!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun handleCreatedListSuccess() {
        // TODO: 6/21/2021
    }

    private fun getCreatedLists() {
        viewModel.getCreatedLists(accountId, API_KEY, sessionId, null, null)
    }
}