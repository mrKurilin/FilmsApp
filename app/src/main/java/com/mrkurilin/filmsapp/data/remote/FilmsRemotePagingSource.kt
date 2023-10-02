package com.mrkurilin.filmsapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mrkurilin.filmsapp.data.remote.model.TopFilmRemote
import javax.inject.Inject

class FilmsRemotePagingSource @Inject constructor(
    private val kinopoiskApiService: KinopoiskApiService
) : PagingSource<Int, TopFilmRemote>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TopFilmRemote> {
        return try {
            val currentPage = params.key ?: 1
            val response = kinopoiskApiService.getTopFilmsRemote(page = currentPage)
            val films = response.films

            val previousKey = if (currentPage == 1) {
                null
            } else {
                currentPage - 1
            }

            val nextKey = if (currentPage == films.size) {
                null
            } else {
                currentPage + 1
            }

            LoadResult.Page(
                data = films,
                prevKey = previousKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TopFilmRemote>): Int? {
        return null
    }
}
