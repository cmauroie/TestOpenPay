package com.fit.popularperson.domain.datasource

import com.fit.core.server.Resource
import com.fit.popularperson.domain.model.ProfileModel

interface RemotePopularPeopleDataSource {
    suspend fun getPopularPeople(): Resource<List<ProfileModel>>
}