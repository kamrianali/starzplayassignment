package com.starzplay.assignment.di

import com.starzplay.networking.MainScreenDataStore
import com.starzplay.networking.MainScreenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {
    @Binds
    fun provideMainScreenRepository(mainScreenRepository: MainScreenRepository): MainScreenDataStore
}