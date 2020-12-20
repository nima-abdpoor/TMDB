package com.nima.tmdb.utils

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun String.toast(context: Context) {

    Toast.makeText(context, this@toast, Toast.LENGTH_SHORT).show()

}