package com.mrkurilin.filmsapp.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mrkurilin.filmsapp.domain.model.Film
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class FilmsDataSource @Inject constructor() : PagingSource<Int, Film>() {

    private val kinopoiskApi: KinopoiskApiService = Retrofit.Builder()
        .baseUrl("https://kinopoiskapiunofficial.tech")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(KinopoiskApiService::class.java)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        return try {
            val currentPage = params.key ?: 1
            val response = kinopoiskApi.getTopFilms(page = currentPage)
            val films = response.films
            LoadResult.Page(
                data = films,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (currentPage == films.size) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Film>): Int? {
        return null
    }
}
