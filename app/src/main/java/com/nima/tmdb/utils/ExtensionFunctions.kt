package com.nima.tmdb.utils

import android.content.Context
import android.util.Log
import android.widget.Toast

fun String.toast(context: Context) {
        Toast.makeText(context, this@toast, Toast.LENGTH_SHORT).show()
}

fun String.log(tag : String){
        Log.d(tag, "log: $this")
}