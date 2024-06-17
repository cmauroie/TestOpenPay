package com.fit.popularperson.data.datasource

import com.fit.core.server.Resource
import com.fit.popularperson.data.dto.KnownForDto
import com.fit.popularperson.data.dto.ResponseDto
import com.fit.popularperson.data.dto.ResultDto
import com.fit.popularperson.data.mappers.toDomainModel
import com.fit.popularperson.data.network.ApiClient
import com.fit.popularperson.domain.datasource.RemotePopularPeopleDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class RemotePopularPeopleDataSourceImpTest {

    private lateinit var apiClient: ApiClient
    private lateinit var dataSource: RemotePopularPeopleDataSource

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        apiClient = mockk()
        dataSource = RemotePopularPeopleDataSourceImp(apiClient)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        io.mockk.clearAllMocks()
    }

    @Test
    fun `getPopularPeople should return Resource Success when apiClient returns data`() =
        testScope.runTest {

            val apiResponse = ResponseDto(
                page = 1,
                results = listOf(
                    ResultDto(
                        id = 123,
                        adult = false,
                        gender = 1,
                        knownForDepartment = "Acting",
                        name = "John Doe",
                        originalName = "John Doe",
                        popularity = 100.0,
                        profilePath = "/path/to/profile.jpg",
                        knownFor = listOf(
                            KnownForDto(
                                originalName = "Original Name",
                                originalTitle = "Original Title",
                                posterPath = "/path/to/poster.jpg",
                                overview = "Overview",
                                popularity = 80.0,
                                name = "name",
                                title = "Title",
                                backdropPath = "/path/to/poster.jpg"
                            )
                        )
                    )
                ),
                totalPages = 1,
                totalResults = 1
            )
            val expectedDomainModel = apiResponse.toDomainModel()
            coEvery { apiClient.getPopularPeople() } returns apiResponse

            val result = dataSource.getPopularPeople()

            assertTrue(result is Resource.Success)
            assertEquals(expectedDomainModel, (result as Resource.Success).value)
            coVerify(exactly = 1) { apiClient.getPopularPeople() }
        }

    @Test
    fun `getPopularPeople should return Resource Failure when apiClient throws IOException`() =
        testScope.runTest {

            val exception = IOException("Network error")
            coEvery { apiClient.getPopularPeople() } throws exception

            val result = dataSource.getPopularPeople()

            assertTrue(result is Resource.Failure)
            coVerify(exactly = 1) { apiClient.getPopularPeople() }
        }

}