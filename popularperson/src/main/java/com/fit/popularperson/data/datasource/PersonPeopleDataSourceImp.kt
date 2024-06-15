package com.fit.popularperson.data.datasource

import com.fit.popularperson.data.mappers.toDomainModel
import com.fit.popularperson.data.network.ApiClient
import com.fit.core.server.Resource
import com.fit.core.server.SafeApiCall
import com.fit.popularperson.domain.datasource.PopularPeopleDataSource
import com.fit.popularperson.domain.model.ProfileModel

class PersonPeopleDataSourceImp constructor(private val apiClient: ApiClient) :
    PopularPeopleDataSource,
    SafeApiCall {
    override suspend fun getPopularPeople(): Resource<List<ProfileModel>> {
        return safeApiCall {
            apiClient.getpopularpeoples().toDomainModel()
        }
    }
}