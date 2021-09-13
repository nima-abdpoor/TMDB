package com.nima.tmdb.business.domain.util

sealed class MediaType {
    data class All(val type : String = "all") : MediaType()
    data class Movie(val type : String = "movie") : MediaType()
    data class Tv(val type : String = "tv") : MediaType()
    data class Person(val type : String = "person") : MediaType()
}