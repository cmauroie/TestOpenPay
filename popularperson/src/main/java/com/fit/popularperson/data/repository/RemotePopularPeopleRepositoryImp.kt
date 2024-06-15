package com.fit.popularperson.data.repository

import com.fit.core.server.Resource
import com.fit.popularperson.domain.datasource.RemotePopularPeopleDataSource
import com.fit.popularperson.domain.model.ProfileModel
import com.fit.popularperson.domain.repository.RemotePopularPeopleRepository
import javax.inject.Inject


class RemotePopularPeopleRepositoryImp @Inject constructor(private val popularPeopleDataSource: RemotePopularPeopleDataSource) :
    RemotePopularPeopleRepository {
    override suspend fun getPopularPeople(): Resource<List<ProfileModel>> =
        popularPeopleDataSource.getPopularPeople()
}