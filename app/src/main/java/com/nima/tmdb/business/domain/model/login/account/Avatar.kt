package com.nima.tmdb.business.domain.model.login.account

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Avatar {
    @SerializedName("gravatar")
    @Expose
    private var gravatar: Gravatar? = null

    @SerializedName("tmdb")
    @Expose
    private var tmdb: Tmdb? = null

    fun getGravatar(): Gravatar? {
        return gravatar
    }

    fun setGravatar(gravatar: Gravatar?) {
        this.gravatar = gravatar
    }

    fun getTmdb(): Tmdb? {
        return tmdb
    }

    fun setTmdb(tmdb: Tmdb?) {
        this.tmdb = tmdb
    }
}