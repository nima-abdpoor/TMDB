package com.nima.tmdb.models

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

}