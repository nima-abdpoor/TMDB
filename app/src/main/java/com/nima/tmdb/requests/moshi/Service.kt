package com.nima.tmdb.requests.moshi

import retrofit2.Retrofit

object Network {
    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://devbytes.udacity.com/")
        .addConverterFactory(.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val devbytes = retrofit.create(DevbyteService::class.java)
}
