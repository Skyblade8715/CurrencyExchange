package com.skycom.currencyexchange.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skycom.currencyexchange.BuildConfig
import com.skycom.currencyexchange.data.remote.api.NbpApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(json: Json): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideNbpApiService(retrofit: Retrofit): NbpApiService {
        return retrofit.create(NbpApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideClock(): java.time.Clock {
        return java.time.Clock.systemDefaultZone()
    }
}

