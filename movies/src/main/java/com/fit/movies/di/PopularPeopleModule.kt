package com.fit.movies.di

import com.fit.movies.data.datasource.RemoteMovieDataSourceImp
import com.fit.movies.data.network.ApiClient
import com.fit.movies.data.repository.RemoteMovieRepositoryImp
import com.fit.movies.domain.datasource.RemoteMovieDataSource
import com.fit.movies.domain.repository.RemoteMovieRepository
import com.fit.movies.domain.usecase.GetMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PopularPeopleModule {

    @Singleton
    @Provides
    fun providerRemoteDataSource(api: ApiClient): RemoteMovieDataSource =
        RemoteMovieDataSourceImp(api)


    @Singleton
    @Provides
    fun provideRemoteRepository(dataSource: RemoteMovieDataSource): RemoteMovieRepository =
        RemoteMovieRepositoryImp(dataSource)

    /*@Singleton
    @Provides
    fun provideLocalDataSource(dao: ProfileDao): LocalPopularPersonDataSource =
        LocalPopularPersonDataSourceImp(dao)

    @Singleton
    @Provides
    fun provideLocalRepository(dataSource: LocalPopularPersonDataSource): LocalPopularPersonRepository =
        LocalPopularPersonRepositoryImp(dataSource)*/

    @Singleton
    @Provides
    fun providerGetPopularPersonUseCase(
        remoteRepository: RemoteMovieRepository
    ): GetMoviesUseCase =
        GetMoviesUseCase(remoteRepository)

}