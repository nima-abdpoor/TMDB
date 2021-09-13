package com.nima.tmdb.business.domain.model.trend

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class TrendModel {
    @SerializedName("adult")
    @Expose
    var adult: Boolean? = null

    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = null

    @SerializedName("genre_ids")
    @Expose
    var genreIds: List<Int>? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("original_language")
    @Expose
    var originalLanguage: String? = null

    @SerializedName("original_title")
    @Expose
    var originalTitle: String? = null

    @SerializedName("overview")
    @Expose
    var overview: String? = null

    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null

    @SerializedName("release_date")
    @Expose
    var releaseDate: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("video")
    @Expose
    var video: Boolean? = null

    @SerializedName("vote_average")
    @Expose
    var voteAverage: Double? = null

    @SerializedName("vote_count")
    @Expose
    var voteCount: Int? = null

    @SerializedName("popularity")
    @Expose
    var popularity: Double? = null

    @SerializedName("first_air_date")
    @Expose
    var firstAirDate: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("origin_country")
    @Expose
    var originCountry: List<String>? = null

    @SerializedName("original_name")
    @Expose
    var originalName: String? = null
    override fun toString(): String {
        return "TrendModel(adult=$adult, backdropPath=$backdropPath, genreIds=$genreIds, id=$id, originalLanguage=$originalLanguage, originalTitle=$originalTitle, overview=$overview, posterPath=$posterPath, releaseDate=$releaseDate, title=$title, video=$video, voteAverage=$voteAverage, voteCount=$voteCount, popularity=$popularity, firstAirDate=$firstAirDate, name=$name, originCountry=$originCountry, originalName=$originalName)"
    }


}