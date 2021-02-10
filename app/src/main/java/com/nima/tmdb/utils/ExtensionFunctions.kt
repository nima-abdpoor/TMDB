package com.nima.tmdb.utils

import android.content.Context
import android.util.Log
import android.widget.Toast

fun String.toast(context: Context ,duration : Int = 0) {
        Toast.makeText(context, this@toast, duration).show()
}

fun String.log(tag : String){
        Log.d(tag, "log: $this")
}