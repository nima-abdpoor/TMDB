package com.nima.tmdb.models.login.account

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class Tmdb {
    @SerializedName("avatar_path")
    @Expose
    private var avatarPath: Any? = null

    fun getAvatarPath(): Any? {
        return avatarPath
    }

    fun setAvatarPath(avatarPath: Any?) {
        this.avatarPath = avatarPath
    }
}
