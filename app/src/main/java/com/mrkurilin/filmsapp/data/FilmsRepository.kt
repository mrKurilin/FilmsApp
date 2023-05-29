package com.mrkurilin.filmsapp.data

import androidx.paging.PagingData
import androidx.paging.map
import com.mrkurilin.filmsapp.data.remote.FilmsRemoteDataSource
import com.mrkurilin.filmsapp.data.remote.mapper.FilmRemoteMapper
import com.mrkurilin.filmsapp.data.remote.mapper.TopFilmRemoteMapper
import com.mrkurilin.filmsapp.data.room.FilmsLocalDataSource
import com.mrkurilin.filmsapp.domain.model.FilmDetails
import com.mrkurilin.filmsapp.domain.model.TopFilm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FilmsRepository @Inject constructor(
    private val filmsRemoteDataSource: FilmsRemoteDataSource,
    private val filmsLocalDataSource: FilmsLocalDataSource,
    private val topFilmRemoteMapper: TopFilmRemoteMapper,
    private val filmRemoteMapper: FilmRemoteMapper,
) {

    fun getTopFilmsPagingDataFlow(): Flow<PagingData<TopFilm>> {
        val topFilmsRemotePagingDataFlow = filmsRemoteDataSource.getTopFilmsRemotePagingDataFlow()

        return topFilmsRemotePagingDataFlow.map { topFilmRemotePagingData ->
            topFilmRemotePagingData.map { topFilmRemote ->
                val isWatched = filmsLocalDataSource.isFilmWatched(topFilmRemote.filmId)
                val isFavourite = filmsLocalDataSource.isFilmFavourite(topFilmRemote.filmId)
                topFilmRemoteMapper.map(
                    topFilmRemote = topFilmRemote,
                    isFavourite = isFavourite,
                    isWatched = isWatched
                )
            }
        }
    }

    suspend fun getFilmById(filmId: Int): FilmDetails {
        val filmRemote = filmsRemoteDataSource.getFilmRemoteById(filmId)
        val isWatched = filmsLocalDataSource.isFilmWatched(filmRemote.kinopoiskId)
        val isFavourite = filmsLocalDataSource.isFilmFavourite(filmRemote.kinopoiskId)

        return filmRemoteMapper.map(
            filmRemote = filmRemote,
            isFavourite = isFavourite,
            isWatched = isWatched
        )
    }
}
