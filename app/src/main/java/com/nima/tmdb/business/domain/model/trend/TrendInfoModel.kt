package com.nima.tmdb.business.domain.model.trend

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class TrendInfoModel {
    @SerializedName("page")
    @Expose
    var page: Int? = null

    @SerializedName("results")
    @Expose
    var results: List<TrendModel>? = null

    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null

    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null
    override fun toString(): String {
        return "TrendInfoModel(page=$page, results=$results, totalPages=$totalPages, totalResults=$totalResults)"
    }

}