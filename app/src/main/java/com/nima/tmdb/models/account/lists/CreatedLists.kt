package com.nima.tmdb.models.account.lists

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CreatedLists {
    @SerializedName("page")
    @Expose
    private val page: Int? = null

    @SerializedName("results")
    @Expose
    val results: List<CreatedListsResult>? = null

    @SerializedName("total_pages")
    @Expose
    private val totalPages: Int? = null

    @SerializedName("total_results")
    @Expose
    private val totalResults: Int? = null
    override fun toString(): String {
        return "CreatedLists(page=$page, results=$results" +
                ", totalPages=$totalPages, totalResults=$totalResults)"
    }


}