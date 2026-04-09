package com.skycom.currencyexchange.di

import com.skycom.currencyexchange.data.repository.CurrencyRepositoryImpl
import com.skycom.currencyexchange.domain.repository.CurrencyRepository
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
    abstract fun bindCurrencyRepository(
        repositoryImpl: CurrencyRepositoryImpl,
    ): CurrencyRepository
}