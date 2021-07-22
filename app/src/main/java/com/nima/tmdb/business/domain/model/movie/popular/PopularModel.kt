package com.nima.tmdb.business.domain.model.movie.popular

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class PopularModel {
    @SerializedName("adult")
    @Expose
     var adult: Boolean? = null

    @SerializedName("backdrop_path")
    @Expose
     var backdropPath: String? = null

    @SerializedName("genre_ids")
    @Expose
     var genreIds: List<Int?>? = null

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

    @SerializedName("popularity")
    @Expose
     var popularity: Double? = null

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
}