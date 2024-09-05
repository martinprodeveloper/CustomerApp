package com.martinprodeveloper.customerapp.di

import com.martinprodeveloper.customerapp.data.repository.ClientRepositoryImpl
import com.martinprodeveloper.customerapp.domain.repository.ClientRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindClientRepository(
        impl: ClientRepositoryImpl
    ): ClientRepository
}