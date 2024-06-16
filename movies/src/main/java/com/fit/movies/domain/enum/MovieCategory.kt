package com.fit.movies.domain.enum

enum class MovieCategory(val id: Int, val path: String) {
    POPULAR(1, "Popular"),
    TOP_RATED(2, "Top Rated"),
    RECOMMENDED(3, "Recommended")
}