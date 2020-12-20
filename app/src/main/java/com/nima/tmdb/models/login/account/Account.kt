package com.nima.tmdb.models.login.account

import android.util.Log
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
const val TAG = "Account"

class Account {

    fun log(tag : String? = TAG) {
        Log.d(tag, "log: ${toString()}")
    }

    override fun toString(): String {
        return "Account(" +
                "avatar=${avatar?.getTmdb()}," +
                " id=$id, iso6391=$iso6391," +
                " iso31661=$iso31661, " +
                "name=$name, " +
                "includeAdult=$includeAdult," +
                " username=$username," +
                " statusMessage=$statusMessage," +
                " statusCode=$statusCode" +
                ")"
    }

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

    @SerializedName("status_message")
    @Expose
    var statusMessage: String? = null

    @SerializedName("status_code")
    @Expose
    var statusCode: Int? = null
}

fun Account.asDatabaseAccount()  :com.nima.tmdb.database.Account{
    return com.nima.tmdb.database.Account(
        id = this.id!!,
        hash = this.avatar?.getGravatar()?.getHash(),
        iso6391 = this.iso6391,
        iso31661 = this.iso31661,
        name = this.name!!,
        includeAdult = this.includeAdult,
        username = this.username!!
    )
}