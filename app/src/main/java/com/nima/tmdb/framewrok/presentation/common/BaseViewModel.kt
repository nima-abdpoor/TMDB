package com.nima.tmdb.framewrok.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nima.tmdb.business.data.network.requests.wrapper.ApiWrapper
import com.nima.tmdb.utils.Constants.TIME_OUT_MEDIUM
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

open class BaseViewModel : ViewModel() {
    private val initStateValue = "Loading"

    fun <T> execute(
        _initStateValue: ApiWrapper<T> = ApiWrapper.Loading(initStateValue),
        function: suspend () -> ApiWrapper<T>
    ): StateFlow<ApiWrapper<T>> {
        val result: StateFlow<ApiWrapper<T>> = flow {
            val response =
                function.invoke()
            emit(response)
        }.stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(TIME_OUT_MEDIUM),
            initialValue = _initStateValue
        )
        return result
    }
}