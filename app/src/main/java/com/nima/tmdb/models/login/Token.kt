package com.nima.tmdb.models.login


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Token {

    @SerializedName("success")
    @Expose
    var success : Boolean = false

    @SerializedName("expires_at")
    @Expose
    var expiresAt : String = ""

    @SerializedName("request_token")
    @Expose
    var requestToken : String? = null

    @SerializedName("status_message")
    @Expose
    var statusMessage: String? = null

    @SerializedName("status_code")
    @Expose
    var statusCode: Int? = null
    override fun toString(): String {
        return "Token(success=$success, expiresAt=$expiresAt, requestToken=$requestToken, statusMessage=$statusMessage, statusCode=$statusCode)"
    }
}