package com.nima.tmdb.business.domain.util

sealed class TimeWindow {
    data class Day(private val time : String = "day"): TimeWindow()
    data class Week(private val time : String = "week"): TimeWindow()
}