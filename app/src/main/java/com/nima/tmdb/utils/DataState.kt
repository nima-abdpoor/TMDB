package com.nima.tmdb.utils

data class DataState<T>(
    var message: Event<String>? = null,
    var data: Event<T>? = null,
    var loading: Boolean = false
) {
    companion object {
        fun <T> error(message: String): DataState<T> {
            return DataState(message = Event(message) , loading = false, data = null)
        }

        fun <T> data(message: String? , data : T?): DataState<T> {
            return DataState(message = Event.messageEvent(message) ,loading = false,data = Event.dataEvent(data))
        }

        fun <T> loading(isLoading : Boolean): DataState<T> {
            return DataState(message = null, loading = isLoading , data = null)
        }
    }

    override fun toString(): String {
        return "DataState(message=$message, data=$data, loading=$loading)"
    }

}