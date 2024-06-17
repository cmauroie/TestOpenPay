package com.fit.popularperson.domain.usecase

import com.fit.core.server.Resource
import com.fit.popularperson.domain.model.KnownForModel
import com.fit.popularperson.domain.model.ProfileModel
import com.fit.popularperson.domain.repository.LocalPopularPersonRepository
import com.fit.popularperson.domain.repository.RemotePopularPeopleRepository
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class GetPopularPersonUseCaseTest {

    private lateinit var remoteRepository: RemotePopularPeopleRepository
    private lateinit var localRepository: LocalPopularPersonRepository
    private lateinit var useCase: GetPopularPersonUseCase

    @Before
    fun setup() {
        remoteRepository = mockk()
        localRepository = mockk()
        useCase = GetPopularPersonUseCase(remoteRepository, localRepository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `invoke should return the first person from remote repository if the response is success and the list is not empty`() = runBlocking {

        val response = Resource.Success(listOf(profileSample))
        coEvery { remoteRepository.getPopularPeople() } returns response
        coEvery { localRepository.clearDatabase() } just Runs
        coEvery { localRepository.insertProfile(profileSample) } just Runs


        val result = useCase.invoke()


        assert(result == profileSample)
        coVerifyOrder {
            remoteRepository.getPopularPeople()
            localRepository.clearDatabase()
            localRepository.insertProfile(profileSample)
        }
        coVerify(exactly = 0) { localRepository.getPopularPerson() }
    }

    @Test
    fun `invoke should return the first person from local repository if the response is success and the list is empty`() = runBlocking {

        val response = Resource.Success(emptyList<ProfileModel>())
        coEvery { remoteRepository.getPopularPeople() } returns response
        coEvery { localRepository.getPopularPerson() } returns flowOf(profileSample)


        val result = useCase.invoke()


        assert(result == profileSample)
        coVerify(exactly = 0) { localRepository.clearDatabase() }
        coVerify(exactly = 0) { localRepository.insertProfile(any()) }
    }

    @Test
    fun `invoke should return the first person from local repository if the response is failure`() = runBlocking {

        val response = Resource.Failure(
            isNetworkError = true,
            errorCode = 404,
            errorBody = null,
            errorDescription = "Not found"
        )
        coEvery { remoteRepository.getPopularPeople() } returns response
        coEvery { localRepository.getPopularPerson() } returns flowOf(profileSample)


        val result = useCase.invoke()


        assert(result == profileSample)
        coVerify(exactly = 0) { localRepository.clearDatabase() }
        coVerify(exactly = 0) { localRepository.insertProfile(any()) }
    }

    private val knownForSample = listOf(
        KnownForModel(
            profileId = 1L,
            originalName = "Original Name 1",
            originalTitle = "Original Title 1",
            posterPath = "/path/to/poster1.jpg",
            overview = "Overview of Movie 1",
            popularity = 75
        ),
        KnownForModel(
            profileId = 2L,
            originalName = "Original Name 2",
            originalTitle = "Original Title 2",
            posterPath = "/path/to/poster2.jpg",
            overview = "Overview of Movie 2",
            popularity = 80
        )
    )

    private val profileSample = ProfileModel(
        adult = false,
        gender = 1,
        id = 12345L,
        knownFor = knownForSample,
        knownForDepartment = "Acting",
        name = "John Doe",
        originalName = "Johnathan Doe",
        popularity = 85,
        profilePath = "/path/to/profile.jpg",
        allTitleMovies = "Original Title 1, Original Title 2"
    )
}