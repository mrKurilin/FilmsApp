package com.mrkurilin.filmsapp.di

import com.mrkurilin.filmsapp.data.remote.KinopoiskApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class DataModule {

    @Provides
    fun provideKinopoiskApiService(): KinopoiskApiService {
        return Retrofit.Builder()
            .baseUrl("https://kinopoiskapiunofficial.tech")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KinopoiskApiService::class.java)
    }
}