package com.fit.popularperson.data.datasource

import com.fit.popularperson.data.db.KnownForEntity
import com.fit.popularperson.data.db.ProfileDao
import com.fit.popularperson.data.db.ProfileEntity
import com.fit.popularperson.data.db.ProfileWithKnownFor
import com.fit.popularperson.data.mappers.toDomainModel
import com.fit.popularperson.data.mappers.toEntityModel
import com.fit.popularperson.domain.datasource.LocalPopularPersonDataSource
import com.fit.popularperson.domain.model.KnownForModel
import com.fit.popularperson.domain.model.ProfileModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LocalPopularPersonDataSourceImpTest {

    private lateinit var dao: ProfileDao
    private lateinit var dataSource: LocalPopularPersonDataSource

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        dao = mockk()
        dataSource = LocalPopularPersonDataSourceImp(dao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        io.mockk.clearAllMocks()
    }

    @Test
    fun `getPopularPerson should return ProfileModel when dao returns ProfileWithKnownFor`() =
        testScope.runTest {

            val profileEntity = ProfileEntity(
                id = 1,
                adult = false,
                gender = 1,
                knownForDepartment = "Acting",
                name = "John Doe",
                originalName = "John Doe",
                popularity = 100,
                profilePath = "/path/to/profile.jpg",
                allTitleMovies = "All Movies"
            )
            val knownForEntityList = listOf(
                KnownForEntity(
                    profileId = 1,
                    originalName = "Original Name",
                    originalTitle = "Original Title",
                    posterPath = "/path/to/poster.jpg",
                    overview = "Overview",
                    popularity = 80
                )
            )
            val profileWithKnownFor = ProfileWithKnownFor(profileEntity, knownForEntityList)
            val profileModel = profileWithKnownFor.toDomainModel()
            coEvery { dao.getFirstProfileWithKnownFor() } returns flow { emit(profileWithKnownFor) }

            val result = dataSource.getPopularPerson().first()

            assertEquals(profileModel, result)
            coVerify(exactly = 1) { dao.getFirstProfileWithKnownFor() }
        }

    @Test
    fun `insertProfile should call dao with ProfileEntity`() = testScope.runTest {

        val profileModel = ProfileModel(
            id = 123,
            adult = false,
            gender = 1,
            knownForDepartment = "Acting",
            name = "John Doe",
            originalName = "John Doe",
            popularity = 100,
            profilePath = "/path/to/profile.jpg",
            allTitleMovies = "All Movies",
            knownFor = emptyList()
        )
        val profileEntity = profileModel.toEntityModel()
        coEvery { dao.insertProfile(any()) } returns Unit

        dataSource.insertProfile(profileModel)

        val slot = slot<ProfileEntity>()
        coVerify(exactly = 1) { dao.insertProfile(capture(slot)) }
        assertEquals(profileEntity, slot.captured)
    }

    @Test
    fun `insertKnownFor should call dao with list of KnownForEntity`() = testScope.runTest {

        val knownForModelList = listOf(
            KnownForModel(
                profileId = 1,
                originalName = "Original Name",
                originalTitle = "Original Title",
                posterPath = "/path/to/poster.jpg",
                overview = "Overview",
                popularity = 80
            )
        )
        val knownForEntityList = knownForModelList.map { it.toEntityModel() }
        coEvery { dao.insertKnownFor(any()) } returns Unit

        dataSource.insertKnownFor(knownForModelList)

        val slot = slot<List<KnownForEntity>>()
        coVerify(exactly = 1) { dao.insertKnownFor(capture(slot)) }
        assertEquals(knownForEntityList, slot.captured)
    }

    @Test
    fun `deleteAll should call dao to delete all profiles and knownFor`() = testScope.runTest {

        coEvery { dao.deleteAllProfiles() } returns Unit
        coEvery { dao.deleteAllAboutKnownFor() } returns Unit

        dataSource.deleteAll()

        coVerify(exactly = 1) { dao.deleteAllProfiles() }
        coVerify(exactly = 1) { dao.deleteAllAboutKnownFor() }
    }
}