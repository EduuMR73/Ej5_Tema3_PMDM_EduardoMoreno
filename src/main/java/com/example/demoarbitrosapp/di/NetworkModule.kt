package com.example.demoarbitrosapp.di

import com.example.demoarbitrosapp.data.ArbitroApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            // La URL base de tu API en Render
            .baseUrl("https://arbitros-api.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideArbitroApiService(retrofit: Retrofit): ArbitroApiService {
        return retrofit.create(ArbitroApiService::class.java)
    }
}
