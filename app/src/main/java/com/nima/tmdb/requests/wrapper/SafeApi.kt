package com.nima.tmdb.requests.wrapper

import com.nima.tmdb.utils.NoInternetException
import kotlinx.coroutines.delay
import retrofit2.Response
import java.net.SocketException
import java.net.UnknownHostException

abstract class SafeApi {
    private var flag: Boolean = false
    suspend fun <T> safeApi(call: suspend () -> Response<T>): ApiWrapper<T> {
        return apiTry { call.invoke() }
    }


    private fun <T> handleResponse(response: Response<T>): ApiWrapper<T> {
        if (response.isSuccessful) {
            response.body()?.let {
                return ApiWrapper.Success(
                    data = it,
                    headers = response.headers(),
                    code = response.code()
                )
            }
        }
        return ApiWrapper.ApiError(
            message = response.message(),
            error = response.errorBody()?.string()!!,
            code = response.code(),
            totalError = "${response.message()} // ${
                response.errorBody()?.string()
            } // ${response.code()}"
        )
    }

    private suspend fun <T> apiTry(call: suspend () -> Response<T>): ApiWrapper<T> {
        return try {
            handleResponse(call.invoke())
        } catch (e: NoInternetException) {
            ApiWrapper.NetworkError(message = "${e.message}")
        } catch (e: UnknownHostException){
            ApiWrapper.NetworkError(message = "${e.message}")
        } catch (e: SocketException){
            ApiWrapper.NetworkError(message = "${e.message}")
        } catch (t: Throwable) {
            if (!flag) {
                flag = true
                delay(3000)
                apiTry(call)
            } else {
                flag = false
                ApiWrapper.UnknownError(message = "${t.message}//${t.cause}")
            }
        }
    }
}