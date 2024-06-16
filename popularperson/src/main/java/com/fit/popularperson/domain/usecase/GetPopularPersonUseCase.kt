package com.fit.popularperson.domain.usecase

import com.fit.core.server.Resource
import com.fit.popularperson.domain.model.ProfileModel
import com.fit.popularperson.domain.repository.LocalPopularPersonRepository
import com.fit.popularperson.domain.repository.RemotePopularPeopleRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GetPopularPersonUseCase @Inject constructor(private val remoteRepository: RemotePopularPeopleRepository,
                                                  private val localRepository: LocalPopularPersonRepository) {
    suspend operator fun invoke(): ProfileModel? {
        return when (val response = remoteRepository.getPopularPeople()) {
            is Resource.Success -> {
                val listPeople = response.value
                if (listPeople.isNotEmpty()) {
                    localRepository.clearDatabase()
                    val a = listPeople[0]
                    localRepository.insertProfile(a)
                    listPeople[0]
                } else {
                    localRepository.getPopularPerson().firstOrNull() ?: run {
                        null
                    }
                }
            }

            is Resource.Failure -> {
                localRepository.getPopularPerson().firstOrNull() ?: run {
                    null
                }
            }
        }
    }
}