package com.nima.tmdb.models.login.account

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Account {
    @SerializedName("avatar")
    @Expose
    var avatar: Avatar? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("iso_639_1")
    @Expose
    var iso6391: String? = null

    @SerializedName("iso_3166_1")
    @Expose
    var iso31661: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("include_adult")
    @Expose
    var includeAdult: Boolean? = null

    @SerializedName("username")
    @Expose
    var username: String? = null
}