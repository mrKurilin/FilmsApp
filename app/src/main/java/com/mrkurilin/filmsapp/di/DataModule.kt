package com.mrkurilin.filmsapp.di

import android.content.Context
import androidx.room.Room
import com.mrkurilin.filmsapp.data.remote.KinopoiskApiService
import com.mrkurilin.filmsapp.data.room.AppDatabase
import com.mrkurilin.filmsapp.data.room.FilmLocalDao
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

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

    @Singleton
    @Provides
    fun provideFilmLocalDao(context: Context): FilmLocalDao = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "database-name"
    ).allowMainThreadQueries().build().filmLocalDao()
}