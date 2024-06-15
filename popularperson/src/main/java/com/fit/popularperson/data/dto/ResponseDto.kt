package com.fit.popularperson.data.dto


import com.google.gson.annotations.SerializedName

data class ResponseDto(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<ResultDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)