package com.nima.tmdb.adapters

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nima.tmdb.R

class ErrorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    @JvmField
    var error: TextView = itemView.findViewById(R.id.error_text)

    @JvmField
    var button: Button = itemView.findViewById(R.id.error_button)

}