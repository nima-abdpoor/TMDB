package com.nima.tmdb.models.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Session {
    @SerializedName("success")
    @Expose
    var success: Boolean = false

    @SerializedName("session_id")
    @Expose
    var sessionId: String? = null


    @SerializedName("status_message")
    @Expose
    var statusMessage: String? = null

    @SerializedName("status_code")
    @Expose
    var statusCode: Int? = null


}