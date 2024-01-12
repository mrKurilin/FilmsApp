package com.mrkurilin.filmsapp.data.remote

import com.mrkurilin.filmsapp.data.remote.model.FilmDetailsRemote
import com.mrkurilin.filmsapp.data.remote.model.TopFilmsRemote
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

private const val ACCEPT = "application/json"

interface KinopoiskApiService {

    @GET("api/v2.2/films/top")
    suspend fun getTopFilmsRemote(
        @Header("X-API-KEY") apiKey: String = API_KEY,
        @Header("accept") accept: String = ACCEPT,
        @Query("type") type: String = "TOP_100_POPULAR_FILMS",
        @Query("page") page: Int = 1,
    ): TopFilmsRemote

    @GET("api/v2.2/films/{id}")
    suspend fun getFilmDetailsRemote(
        @Header("X-API-KEY") apiKey: String = API_KEY,
        @Header("accept") accept: String = ACCEPT,
        @Path("id") id: Int,
    ): FilmDetailsRemote
}