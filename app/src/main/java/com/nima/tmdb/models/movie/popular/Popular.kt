package com.nima.tmdb.models.movie.popular

import com.squareup.moshi.Json


class Popular {
    @Json(name = "page")
    var page: Int? = null

    @Json(name = "results")
    var results: List<Result>? = null

    @Json(name = "total_pages")
    var totalPages: Int? = null

    @Json(name = "total_results")
    var totalResults: Int? = null
}
