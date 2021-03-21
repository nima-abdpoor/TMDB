package com.nima.tmdb.models.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FavoriteResponse {
    @SerializedName("success")
    @Expose
    var success: Boolean = false

    @SerializedName("status_message")
    @Expose
    var statusMessage: String? = null

    @SerializedName("status_code")
    @Expose
    var statusCode: Int? = null
}