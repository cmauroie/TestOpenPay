package com.fit.popularperson.di

import com.fit.popularperson.data.datasource.LocalPopularPersonDataSourceImp
import com.fit.popularperson.data.datasource.RemotePopularPeopleDataSourceImp
import com.fit.popularperson.data.db.ProfileDao
import com.fit.popularperson.data.network.ApiClient
import com.fit.popularperson.data.repository.LocalPopularPersonRepositoryImp
import com.fit.popularperson.data.repository.RemotePopularPeopleRepositoryImp
import com.fit.popularperson.domain.datasource.LocalPopularPersonDataSource
import com.fit.popularperson.domain.datasource.RemotePopularPeopleDataSource
import com.fit.popularperson.domain.repository.LocalPopularPersonRepository
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
    fun providerRemoteDataSource(api: ApiClient): RemotePopularPeopleDataSource =
        RemotePopularPeopleDataSourceImp(api)


    @Singleton
    @Provides
    fun provideRemoteRepository(dataSource: RemotePopularPeopleDataSource): RemotePopularPeopleRepository =
        RemotePopularPeopleRepositoryImp(dataSource)

    @Singleton
    @Provides
    fun provideLocalDataSource(dao: ProfileDao): LocalPopularPersonDataSource =
        LocalPopularPersonDataSourceImp(dao)

    @Singleton
    @Provides
    fun provideLocalRepository(dataSource: LocalPopularPersonDataSource): LocalPopularPersonRepository =
        LocalPopularPersonRepositoryImp(dataSource)

    @Singleton
    @Provides
    fun providerGetPopularPersonUseCase(
        remoteRepository: RemotePopularPeopleRepository,
        localRepository: LocalPopularPersonRepository
    ): GetPopularPersonUseCase =
        GetPopularPersonUseCase(remoteRepository, localRepository)

}