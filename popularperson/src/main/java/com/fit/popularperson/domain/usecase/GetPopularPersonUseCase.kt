package com.fit.popularperson.domain.usecase

import com.fit.core.server.Resource
import com.fit.popularperson.domain.model.ProfileModel
import com.fit.popularperson.domain.repository.RemotePopularPeopleRepository
import javax.inject.Inject

class GetPopularPersonUseCase @Inject constructor(private val repository: RemotePopularPeopleRepository) {
    suspend operator fun invoke(): ProfileModel? {
        return when (val response = repository.getPopularPeople()) {
            is Resource.Success -> {
                val listPeople = response.value
                if (listPeople.isNotEmpty()) {
                    listPeople[0]
                } else {
                    null
                }
            }
            is Resource.Failure -> {
                null
            }
        }
    }
}