package com.fit.popularperson.data.datasource

import com.fit.popularperson.data.db.ProfileDao
import com.fit.popularperson.data.mappers.toDomainModel
import com.fit.popularperson.data.mappers.toEntityModel
import com.fit.popularperson.domain.datasource.LocalPopularPersonDataSource
import com.fit.popularperson.domain.model.KnownForModel
import com.fit.popularperson.domain.model.ProfileModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class LocalPopularPersonDataSourceImp @Inject constructor(private val dao: ProfileDao) :
    LocalPopularPersonDataSource {
    override suspend fun getPopularPerson(): Flow<ProfileModel?> {
        return dao.getFirstProfileWithKnownFor().map {
            it?.toDomainModel()
        }
    }

    override suspend fun insertProfile(profile: ProfileModel) {
        dao.insertProfile(profile.toEntityModel())
    }

    override suspend fun insertKnownFor(knownFor: List<KnownForModel>) {
        dao.insertKnownFor(knownFor.map { it.toEntityModel() })
    }

    override suspend fun deleteAll() {
        dao.deleteAllProfiles()
        dao.deleteAllAboutKnownFor()
    }
}