package com.fit.movies.domain.usecase

import com.fit.core.server.Resource
import com.fit.movies.domain.model.CategoryModel
import com.fit.movies.domain.repository.LocalMovieRepository
import com.fit.movies.domain.repository.RemoteMovieRepository
import kotlinx.coroutines.flow.firstOrNull

import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val remoteRepository: RemoteMovieRepository,
    private val localRepository: LocalMovieRepository
) {
    suspend operator fun invoke(): List<CategoryModel> {
        val categories = mutableListOf<CategoryModel>()

        /*val response = remoteRepository.getPopularMovies()
        if (response is Resource.Success) {
            categories.add(response.value)
        }*/

        when (val response = remoteRepository.getPopularMovies()) {
            is Resource.Success -> {
                categories.add(response.value)
            }

            is Resource.Failure -> {}
        }
        when (val response = remoteRepository.getTopRatedMovies()) {
            is Resource.Success -> {
                categories.add(response.value)
            }

            is Resource.Failure -> {}
        }
        when (val response = remoteRepository.getRecommendationsMovies()) {
            is Resource.Success -> {
                categories.add(response.value)
            }

            is Resource.Failure -> {}
        }

        if (categories.isNotEmpty()) {
            localRepository.clearDatabase()
            categories.forEach { category ->
                localRepository.insertCategory(category)
            }
            return categories
        } else {
            return localRepository.getAllMovies().firstOrNull() ?: run {
                emptyList()
            }
        }
    }
}