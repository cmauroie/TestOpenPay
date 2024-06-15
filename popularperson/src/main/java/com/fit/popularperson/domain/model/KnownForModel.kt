package com.fit.popularperson.domain.model


data class KnownForModel(
    val profileId:Long,
    val originalName: String,
    val originalTitle: String,
    val posterPath: String,
    val overview: String,
    val popularity: Int,
)