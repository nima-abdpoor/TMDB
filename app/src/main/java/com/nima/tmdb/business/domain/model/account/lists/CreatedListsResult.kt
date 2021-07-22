package com.nima.tmdb.business.domain.model.account.lists

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class CreatedListsResult {
    @SerializedName("description")
    @Expose
    val description: String? = null

    @SerializedName("favorite_count")
    @Expose
    val favoriteCount: Int? = null

    @SerializedName("id")
    @Expose
    val id: Int? = null

    @SerializedName("item_count")
    @Expose
    val itemCount: Int? = null

    @SerializedName("iso_639_1")
    @Expose
    val iso6391: String? = null

    @SerializedName("list_type")
    @Expose
    val listType: String? = null

    @SerializedName("name")
    @Expose
    val name: String? = null

    @SerializedName("poster_path")
    @Expose
    val posterPath: Any? = null
    override fun toString(): String {
        return "CreatedListsResult(description=$description," +
                " favoriteCount=$favoriteCount," +
                " id=$id, itemCount=$itemCount," +
                " iso6391=$iso6391," +
                " listType=$listType, " +
                "name=$name, " +
                "posterPath=$posterPath)\n\n"
    }


}