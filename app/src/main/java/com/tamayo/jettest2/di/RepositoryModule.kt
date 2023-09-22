package com.tamayo.jettest2.di

import com.tamayo.jettest2.data.rest.GifsRepository
import com.tamayo.jettest2.data.rest.GifsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(gifsRepositoryImpl: GifsRepositoryImpl): GifsRepository
}