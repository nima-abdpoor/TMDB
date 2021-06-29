package com.nima.tmdb.models.account.lists

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class CreatedListsResult {
    @SerializedName("description")
    @Expose
    private val description: String? = null

    @SerializedName("favorite_count")
    @Expose
    private val favoriteCount: Int? = null

    @SerializedName("id")
    @Expose
    private val id: Int? = null

    @SerializedName("item_count")
    @Expose
    private val itemCount: Int? = null

    @SerializedName("iso_639_1")
    @Expose
    private val iso6391: String? = null

    @SerializedName("list_type")
    @Expose
    private val listType: String? = null

    @SerializedName("name")
    @Expose
    private val name: String? = null

    @SerializedName("poster_path")
    @Expose
    private val posterPath: Any? = null
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