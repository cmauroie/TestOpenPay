package com.fit.popularperson.data.repository

import com.fit.core.server.Resource
import com.fit.popularperson.domain.datasource.PopularPeopleDataSource
import com.fit.popularperson.domain.model.ProfileModel
import com.fit.popularperson.domain.repository.PopularPeopleRepository


class PopularPeopleRepositoryImp constructor(private val popularPeopleDataSource: PopularPeopleDataSource) :
    PopularPeopleRepository {
    override suspend fun getPopularPeople(): Resource<List<ProfileModel>> =
        popularPeopleDataSource.getPopularPeople()
}