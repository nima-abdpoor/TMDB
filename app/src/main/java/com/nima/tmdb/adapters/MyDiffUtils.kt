package com.nima.tmdb.adapters

import androidx.recyclerview.widget.DiffUtil
import com.nima.tmdb.models.Result


class MyDiffUtils(private val OldResult: List<Result>, private val NewResult: List<Result>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return OldResult.size
    }

    override fun getNewListSize(): Int {
        return NewResult.size
    }

    override fun areItemsTheSame(i: Int, i1: Int): Boolean {
        return OldResult[i] === NewResult[i1]
    }

    override fun areContentsTheSame(i: Int, i1: Int): Boolean {
        return OldResult[i] == NewResult[i1]
    }
}