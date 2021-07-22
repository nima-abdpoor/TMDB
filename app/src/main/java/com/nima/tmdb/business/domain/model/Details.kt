package com.nima.tmdb.business.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Details {
    var error : String? = null

    @SerializedName("adult")
    @Expose
    var adult: Boolean? = null

    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = null

    @SerializedName("genres")
    @Expose
    var genres: List<Genre>? = null

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

    @SerializedName("production_companies")
    @Expose
    var productionCompanies: List<ProductionCompany>? = null

    @SerializedName("production_countries")
    @Expose
    var productionCountries: List<ProductionCountry>? = null

    @SerializedName("release_date")
    @Expose
    var releaseDate: String? = null

    //    @SerializedName("spoken_languages")
    //    @Expose
    //    private List<SpokenLanguage> spokenLanguages = null;
    @SerializedName("status")
    @Expose
    private var status: String? = null

    @SerializedName("tagline")
    @Expose
    var tagline: String? = null

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

    //    public List<SpokenLanguage> getSpokenLanguages() {
    //        return spokenLanguages;
    //    }
    //
    //    public void setSpokenLanguages(List<SpokenLanguage> spokenLanguages) {
    //        this.spokenLanguages = spokenLanguages;
    //    }
    //
    //    public String getStatus() {
    //        return status;
    //    }
    fun setStatus(status: String?) {
        this.status = status
    }
    fun setErrorMessage(message : String ){
        this.error = message
    }
}