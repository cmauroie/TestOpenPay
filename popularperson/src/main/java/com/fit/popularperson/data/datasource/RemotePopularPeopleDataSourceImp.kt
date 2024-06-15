package com.fit.popularperson.data.datasource

import com.fit.popularperson.data.mappers.toDomainModel
import com.fit.popularperson.data.network.ApiClient
import com.fit.core.server.Resource
import com.fit.core.server.SafeApiCall
import com.fit.popularperson.domain.datasource.RemotePopularPeopleDataSource
import com.fit.popularperson.domain.model.ProfileModel
import javax.inject.Inject

class RemotePopularPeopleDataSourceImp @Inject constructor(private val apiClient: ApiClient) :
    RemotePopularPeopleDataSource,
    SafeApiCall {
    override suspend fun getPopularPeople(): Resource<List<ProfileModel>> {
        return safeApiCall {
            apiClient.getpopularpeoples().toDomainModel()
        }
    }
}