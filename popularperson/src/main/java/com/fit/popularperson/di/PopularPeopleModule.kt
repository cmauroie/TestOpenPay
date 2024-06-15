package com.fit.popularperson.di

import com.fit.popularperson.data.datasource.RemotePopularPeopleDataSourceImp
import com.fit.popularperson.data.network.ApiClient
import com.fit.popularperson.data.repository.RemotePopularPeopleRepositoryImp
import com.fit.popularperson.domain.datasource.RemotePopularPeopleDataSource
import com.fit.popularperson.domain.repository.RemotePopularPeopleRepository
import com.fit.popularperson.domain.usecase.GetPopularPersonUseCase
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
    fun providerDataSource(api: ApiClient): RemotePopularPeopleDataSource =
        RemotePopularPeopleDataSourceImp(api)


    @Singleton
    @Provides
    fun provideRepository(dataSource: RemotePopularPeopleDataSource): RemotePopularPeopleRepository =
        RemotePopularPeopleRepositoryImp(dataSource)

    @Singleton
    @Provides
    fun providerGetPopularPersonUseCase(repository: RemotePopularPeopleRepository): GetPopularPersonUseCase =
        GetPopularPersonUseCase(repository)

}