package com.fit.popularperson.data.mappers

import com.fit.core.URL_BASE_IMAGE
import com.fit.popularperson.data.dto.KnownForDto
import com.fit.popularperson.data.dto.ResponseDto
import com.fit.popularperson.data.dto.ResultDto
import com.fit.popularperson.domain.model.KnownForModel
import com.fit.popularperson.domain.model.ProfileModel

fun ResponseDto.toDomainModel(): List<ProfileModel> {
    return this.results.map { it.toDomainModel() }
}


fun ResultDto.toDomainModel(): ProfileModel {
    val knownForModelList = this.knownFor.map { it.toDomainModel() }
    return ProfileModel(
        this.adult,
        this.gender,
        this.id,
        knownForModelList,
        this.knownForDepartment,
        this.name,
        this.originalName,
        this.popularity.toInt(),
        "$URL_BASE_IMAGE${this.profilePath}",
        getConcatenatedTitles(knownForModelList)
    )
}

fun KnownForDto.toDomainModel(): KnownForModel {
    return KnownForModel(
        this.originalName ?: "",
        this.title ?: this.name ?: this.originalName ?: "",
        "$URL_BASE_IMAGE${this.backdropPath}",
        this.overview,
        this.popularity.toInt()
    )
}

fun getConcatenatedTitles(movies: List<KnownForModel>): String {
    return movies.joinToString(",\n") { it.originalTitle }
}

