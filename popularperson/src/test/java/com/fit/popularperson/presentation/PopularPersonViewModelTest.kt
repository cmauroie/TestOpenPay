package com.fit.popularperson.presentation

import com.fit.popularperson.domain.model.KnownForModel
import com.fit.popularperson.domain.model.ProfileModel
import com.fit.popularperson.domain.usecase.GetPopularPersonUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi


import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PopularPersonViewModelTest{
    private lateinit var useCase: GetPopularPersonUseCase
    private lateinit var viewModel: PopularPersonViewModel

    private val testScheduler = TestCoroutineScheduler()
    private val testDispatcher = StandardTestDispatcher(testScheduler)
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        useCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        io.mockk.clearAllMocks()
    }

    @Test
    fun `fetchMovies should emit Loading and then ShowView when useCase returns data`() = testScope.runTest {
        // Arrange
        val knownForModel = KnownForModel(
            profileId = 1,
            originalName = "Original Name",
            originalTitle = "Original Title",
            posterPath = "/path/to/poster.jpg",
            overview = "Overview",
            popularity = 80
        )
        val profileModel = ProfileModel(
            adult = false,
            gender = 1,
            id = 123,
            knownFor = listOf(knownForModel),
            knownForDepartment = "Acting",
            name = "John Doe",
            originalName = "John Doe",
            popularity = 100,
            profilePath = "/path/to/profile.jpg",
            allTitleMovies = "All Movies"
        )
        coEvery { useCase() } returns profileModel

        viewModel = PopularPersonViewModel(useCase)
        val emissions = viewModel.uiModel.take(2).toList()

        assertEquals(2, emissions.size)

        val loadingState = emissions[0]
        assertTrue(loadingState is PopularPersonViewModel.UIModel.Loading)

        val showViewState = emissions[1]
        assertTrue(showViewState is PopularPersonViewModel.UIModel.ShowView)
        val showViewProfileModel = (showViewState as PopularPersonViewModel.UIModel.ShowView).profileModel
        assertEquals(profileModel, showViewProfileModel)

        coVerify(exactly = 1) { useCase() }
    }

    @Test
    fun `fetchMovies should emit Loading and then NoConnection when useCase returns null`() = testScope.runTest {

        coEvery { useCase() } returns null

        viewModel = PopularPersonViewModel(useCase)
        val emissions = viewModel.uiModel.take(2).toList()

        assertEquals(2, emissions.size)

        val loadingState = emissions[0]
        assertTrue(loadingState is PopularPersonViewModel.UIModel.Loading)

        val noConnectionState = emissions[1]
        assertTrue(noConnectionState is PopularPersonViewModel.UIModel.NoConnection)

        coVerify(exactly = 1) { useCase() }
    }
}
