package com.mrkurilin.filmsapp.di

import com.mrkurilin.filmsapp.data.network.KinopoiskApiService
import com.mrkurilin.filmsapp.data.room.FavouriteFilmsDataBase
import com.mrkurilin.filmsapp.data.room.RoomFavouriteFilmsDataBase
import com.mrkurilin.filmsapp.data.room.RoomWatchedFilmsDataBase
import com.mrkurilin.filmsapp.data.room.WatchedFilmsDataBase
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppModule {

    @Provides
    fun provideRoomWatchedFilmsDataBase(): WatchedFilmsDataBase {
        return RoomWatchedFilmsDataBase()
    }

    @Provides
    fun provideRoomFavouriteFilmsDataBase(): FavouriteFilmsDataBase {
        return RoomFavouriteFilmsDataBase()
    }

    @Provides
    fun provideKinopoiskApiService(): KinopoiskApiService {
        return Retrofit.Builder()
            .baseUrl("https://kinopoiskapiunofficial.tech")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KinopoiskApiService::class.java)
    }
}