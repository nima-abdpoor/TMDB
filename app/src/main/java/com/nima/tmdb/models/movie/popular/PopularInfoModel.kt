package com.nima.tmdb.models.movie.popular

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class PopularInfoModel {

    @SerializedName("page")
    @Expose
    var page: Int? = null

    @SerializedName("results")
    @Expose
    var results: List<PopularModel?>? = null

    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null

    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null


    override fun toString(): String {
        return "PopularInfoModel(page=$page, results=$results, totalPages=$totalPages, totalResults=$totalResults)"
    }


}
