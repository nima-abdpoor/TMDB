package com.nima.tmdb.uiHelpers

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DrawGlide {
    fun draw(context: Context?, requestOptions: RequestOptions?, loadAddress: String?, into: ImageView?) {
        Glide.with(context!!)
                .setDefaultRequestOptions(requestOptions!!)
                .load(loadAddress)
                .into(into!!)
    }
}