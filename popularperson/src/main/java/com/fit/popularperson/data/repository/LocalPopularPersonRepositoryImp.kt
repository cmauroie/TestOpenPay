package com.fit.popularperson.data.repository

import com.fit.popularperson.domain.datasource.LocalPopularPersonDataSource
import com.fit.popularperson.domain.model.KnownForModel
import com.fit.popularperson.domain.model.ProfileModel
import com.fit.popularperson.domain.repository.LocalPopularPersonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalPopularPersonRepositoryImp @Inject constructor(private val dataSource: LocalPopularPersonDataSource) :
    LocalPopularPersonRepository {
    override suspend fun getPopularPerson(): Flow<ProfileModel?> {
        return dataSource.getPopularPerson()
    }

    override suspend fun insertProfile(profile: ProfileModel) {
        dataSource.insertProfile(profile)
        dataSource.insertKnownFor(profile.knownFor)
    }

    override suspend fun insertKnownFor(knownFor: List<KnownForModel>) {
        dataSource.insertKnownFor(knownFor)
    }

    override suspend fun clearDatabase() {
        dataSource.deleteAll()
    }

}