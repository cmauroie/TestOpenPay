package com.fit.movies.di

import com.fit.movies.data.datasource.LocalMovieDataSourceImp
import com.fit.movies.data.datasource.RemoteMovieDataSourceImp
import com.fit.movies.data.db.CategoryDao
import com.fit.movies.data.network.ApiClient
import com.fit.movies.data.repository.LocalMovieRepositoryImp
import com.fit.movies.data.repository.RemoteMovieRepositoryImp
import com.fit.movies.domain.datasource.LocalMovieDataSource
import com.fit.movies.domain.datasource.RemoteMovieDataSource
import com.fit.movies.domain.repository.LocalMovieRepository
import com.fit.movies.domain.repository.RemoteMovieRepository
import com.fit.movies.domain.usecase.GetMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieModule {

    @Singleton
    @Provides
    fun providerRemoteDataSource(api: ApiClient): RemoteMovieDataSource =
        RemoteMovieDataSourceImp(api)


    @Singleton
    @Provides
    fun provideRemoteRepository(dataSource: RemoteMovieDataSource): RemoteMovieRepository =
        RemoteMovieRepositoryImp(dataSource)

    @Singleton
    @Provides
    fun provideLocalDataSource(dao: CategoryDao): LocalMovieDataSource =
        LocalMovieDataSourceImp(dao)

    @Singleton
    @Provides
    fun provideLocalRepository(dataSource: LocalMovieDataSource): LocalMovieRepository =
        LocalMovieRepositoryImp(dataSource)

    @Singleton
    @Provides
    fun providerGetPopularPersonUseCase(
        remoteRepository: RemoteMovieRepository,
        localRepository: LocalMovieRepository
    ): GetMoviesUseCase =
        GetMoviesUseCase(remoteRepository, localRepository)
}