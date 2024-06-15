package com.fit.popularperson.domain.model

data class ProfileModel(
    val adult: Boolean,
    val gender: Int,
    val id: Long,
    val knownFor: List<KnownForModel>,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Int,
    val profilePath: String,
    val allTitleMovies: String,
)