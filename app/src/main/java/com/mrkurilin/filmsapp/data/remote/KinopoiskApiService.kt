package com.mrkurilin.filmsapp.data.remote

import com.mrkurilin.filmsapp.data.remote.model.FilmDetailsRemote
import com.mrkurilin.filmsapp.data.remote.model.TopFilmsRemote
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

private const val API_KEY = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"
//private const val API_KEY = "4bc419af-6c6b-4878-94ae-cc14684137e9"
private const val ACCEPT = "application/json"
private const val TYPE = "TOP_100_POPULAR_FILMS"

interface KinopoiskApiService {

    @GET("api/v2.2/films/top")
    suspend fun getTopFilmsRemote(
        @Header("X-API-KEY") apiKey: String = API_KEY,
        @Header("accept") accept: String = ACCEPT,
        @Query("type") type: String = TYPE,
        @Query("page") page: Int = 1,
    ): TopFilmsRemote

    @GET("api/v2.2/films/{id}")
    suspend fun getFilmDetailsRemote(
        @Header("X-API-KEY") apiKey: String = API_KEY,
        @Header("accept") accept: String = ACCEPT,
        @Path("id") id: Int,
    ): FilmDetailsRemote
}