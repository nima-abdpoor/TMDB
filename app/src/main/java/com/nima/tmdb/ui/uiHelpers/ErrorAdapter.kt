package com.nima.tmdb.ui.uiHelpers

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nima.tmdb.R
import kotlinx.android.synthetic.main.error_in_list_movies.view.*

class ErrorAdapter(private val tryAgain: TryAgain? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("TAG", "handleErrorData: done")
        return ErrorViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.error_in_list_movies,
                parent,
                false
            )
        ,tryAgain
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ErrorViewHolder){
            holder.bind()
            Log.d("TAG", "handleErrorData: ++++++")
        }
//        when (holder) {
//            is ErrorViewHolder -> {
//               holder.bind()
//            }
//        }
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
, private val tryAgain : ErrorAdapter.TryAgain?
) : RecyclerView.ViewHolder(itemView) {
    val TAG = "MovieListViewHolder"
    fun bind() = with(itemView) {
        itemView.error_progress.visibility = View.INVISIBLE
        setupView(itemView)
    }

    private fun setupView(itemView: View) {
        itemView.error_button.setOnClickListener {
            itemView.error_progress.visibility = View.VISIBLE
            tryAgain?.onClick()
        }
    }
}
