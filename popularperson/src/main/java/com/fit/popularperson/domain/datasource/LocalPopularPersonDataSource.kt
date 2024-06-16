package com.fit.popularperson.domain.datasource

import com.fit.popularperson.domain.model.KnownForModel
import com.fit.popularperson.domain.model.ProfileModel
import kotlinx.coroutines.flow.Flow

interface LocalPopularPersonDataSource {
    suspend fun getPopularPerson(): Flow<ProfileModel?>
    suspend fun insertProfile(profile: ProfileModel)
    suspend fun insertKnownFor(knownFor: List<KnownForModel>)
    suspend fun deleteAll()
}