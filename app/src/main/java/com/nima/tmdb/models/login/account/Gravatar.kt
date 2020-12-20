package com.nima.tmdb.models.login.account

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Gravatar {
    @SerializedName("hash")
    @Expose
    private var hash: String? = null

    fun getHash(): String? {
        return hash
    }

    fun setHash(hash: String?) {
        this.hash = hash
    }
}
