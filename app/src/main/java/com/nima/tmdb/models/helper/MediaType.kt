package com.nima.tmdb.models.helper

sealed class MediaType {
    class All(type : String = "all") : MediaType()
    class Movie(type : String = "movie") : MediaType()
    class Tv(type : String = "tv") : MediaType()
    class Person(type : String = "person") : MediaType()
}