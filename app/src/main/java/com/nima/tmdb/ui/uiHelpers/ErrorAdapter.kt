package com.nima.tmdb.ui.uiHelpers

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nima.tmdb.R

class ErrorAdapter(private val tryAgain: TryAgain) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("TAG", "handleErrorData: done")
        return ErrorViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.error_in_list_movies,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 1
    }

    interface TryAgain {
        fun onClick()
    }

}
class ErrorViewHolder
constructor(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    val TAG = "MovieListViewHolder"
}
