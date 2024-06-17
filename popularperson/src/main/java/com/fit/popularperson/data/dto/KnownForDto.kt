package com.fit.popularperson.data.dto


import com.google.gson.annotations.SerializedName

data class KnownForDto(
    @SerializedName("original_name")
    val originalName: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("title")
    val title: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String,
)