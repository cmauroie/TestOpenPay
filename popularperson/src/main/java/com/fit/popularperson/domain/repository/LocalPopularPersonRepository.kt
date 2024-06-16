package com.fit.popularperson.domain.repository

import com.fit.popularperson.domain.model.KnownForModel
import com.fit.popularperson.domain.model.ProfileModel
import kotlinx.coroutines.flow.Flow

interface LocalPopularPersonRepository {
    suspend fun getPopularPerson(): Flow<ProfileModel?>

    suspend fun insertProfile(profile: ProfileModel)

    suspend fun insertKnownFor(knownFor: List<KnownForModel>)
    suspend fun clearDatabase()
}