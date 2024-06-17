package com.fit.popularperson.data.mappers


import com.fit.core.BuildConfig
import com.fit.popularperson.data.db.KnownForEntity
import com.fit.popularperson.data.db.ProfileEntity
import com.fit.popularperson.data.db.ProfileWithKnownFor
import com.fit.popularperson.data.dto.KnownForDto
import com.fit.popularperson.data.dto.ResponseDto
import com.fit.popularperson.data.dto.ResultDto
import com.fit.popularperson.domain.model.KnownForModel
import com.fit.popularperson.domain.model.ProfileModel

fun ResponseDto.toDomainModel(): List<ProfileModel> {
    return this.results.map { it.toDomainModel() }
}


fun ResultDto.toDomainModel(): ProfileModel {
    val knownForModelList = this.knownFor.map { it.toDomainModel((this.id)) }
    return ProfileModel(
        this.adult,
        this.gender,
        this.id,
        knownForModelList,
        this.knownForDepartment,
        this.name,
        this.originalName,
        this.popularity.toInt(),
        "${BuildConfig.URL_BASE_IMAGE}${this.profilePath}",
        getConcatenatedTitles(knownForModelList)
    )
}

fun KnownForDto.toDomainModel(profileID: Long): KnownForModel {
    return KnownForModel(
        profileID,
        this.originalName ?: "",
        this.title ?: this.name ?: this.originalName ?: "",
        "${BuildConfig.URL_BASE_IMAGE}${this.backdropPath}",
        this.overview,
        this.popularity.toInt()
    )
}

fun getConcatenatedTitles(movies: List<KnownForModel>): String {
    return movies.joinToString(",\n") { it.originalTitle }
}


fun ProfileWithKnownFor.toDomainModel():ProfileModel{
    val knownForModelList = this.knownFor.map { it.toDomainModel() }
    this.profile.apply {
        return ProfileModel(
            this.adult,
            this.gender,
            this.id,
            knownForModelList,
            this.knownForDepartment,
            this.name,
            this.originalName,
            this.popularity,
            this.profilePath,
            getConcatenatedTitles(knownForModelList)
        )
    }
}

fun KnownForEntity.toDomainModel(): KnownForModel {
    return KnownForModel(
        this.profileId,
        this.originalName,
        this.originalTitle,
        this.posterPath,
        this.overview,
        this.popularity
    )
}

fun ProfileModel.toEntityModel(): ProfileEntity {
    return ProfileEntity(
        this.id,
        this.adult,
        this.gender,
        this.knownForDepartment,
        this.name,
        this.originalName,
        this.popularity,
        this.profilePath,
        this.allTitleMovies
    )
}

fun KnownForModel.toEntityModel():KnownForEntity{
    return KnownForEntity(
        profileId = this.profileId,
        originalName = this.originalName,
        originalTitle = this.originalTitle,
        posterPath = this.posterPath,
        overview = this.overview,
        popularity = this.popularity
    )
}

