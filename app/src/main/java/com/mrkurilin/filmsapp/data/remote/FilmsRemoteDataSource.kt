package com.mrkurilin.filmsapp.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mrkurilin.filmsapp.data.remote.model.FilmRemote
import com.mrkurilin.filmsapp.data.remote.model.TopFilmRemote
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGE_SIZE = 20

class FilmsRemoteDataSource @Inject constructor(
    private val kinopoiskApi: KinopoiskApiService,
) {

    suspend fun getFilmRemoteById(filmId: Int): FilmRemote {
        return kinopoiskApi.getFilmRemote(id = filmId)
    }

    fun getTopFilmsRemotePagingDataFlow(): Flow<PagingData<TopFilmRemote>> {
        val filmsRemotePagingSource = FilmsRemotePagingSource(kinopoiskApi)
        val config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false
        )

        val filmsPager = Pager(
            config = config,
            pagingSourceFactory = {
                filmsRemotePagingSource
            }
        )

        return filmsPager.flow
    }
}