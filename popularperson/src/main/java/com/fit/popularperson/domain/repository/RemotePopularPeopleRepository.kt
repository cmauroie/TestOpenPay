package com.fit.popularperson.domain.repository

import com.fit.core.server.Resource
import com.fit.popularperson.domain.model.ProfileModel


interface RemotePopularPeopleRepository {
    suspend fun getPopularPeople(): Resource<List<ProfileModel>>
}