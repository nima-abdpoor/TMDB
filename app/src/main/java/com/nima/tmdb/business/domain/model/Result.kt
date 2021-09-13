package com.nima.tmdb.business.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Result {
    var error : String? = null

    @SerializedName("popularity")
    @Expose
    var popularity: Double? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("original_language")
    @Expose
    var originalLanguage: String? = null

    @SerializedName("original_title")
    @Expose
    var originalTitle: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("release_date")
    @Expose
    var release_date: String? = null

    @SerializedName("overview")
    @Expose
    var overview: String? = null

    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null

    fun setErrorMessage(message : String){
        this.error = message
    }
}