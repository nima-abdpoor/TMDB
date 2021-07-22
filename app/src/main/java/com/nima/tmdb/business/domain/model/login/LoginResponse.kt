package com.nima.tmdb.business.domain.model.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse {
    @SerializedName("success")
    @Expose
    var success: Boolean = false
    @SerializedName("expires_at")
    @Expose
    var expiresAt: String? = null
    @SerializedName("request_token")
    @Expose
    var requestToken: String? = null

    @SerializedName("status_message")
    @Expose
    var statusMessage: String? = null

    @SerializedName("status_code")
    @Expose
    var statusCode: Int? = null

}