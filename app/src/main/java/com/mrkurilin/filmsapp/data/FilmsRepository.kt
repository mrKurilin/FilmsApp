package com.mrkurilin.filmsapp.data

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.mrkurilin.filmsapp.data.remote.FilmsRemoteDataSource
import com.mrkurilin.filmsapp.data.remote.mapper.FilmDetailsRemoteMapper
import com.mrkurilin.filmsapp.data.remote.mapper.TopFilmRemoteMapper
import com.mrkurilin.filmsapp.data.room.FilmsLocalDataSource
import com.mrkurilin.filmsapp.data.room.model.FilmStatusLocal
import com.mrkurilin.filmsapp.domain.model.FilmDetails
import com.mrkurilin.filmsapp.domain.model.TopFilm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FilmsRepository @Inject constructor(
    private val filmsRemoteDataSource: FilmsRemoteDataSource,
    private val filmsLocalDataSource: FilmsLocalDataSource,
    private val topFilmRemoteMapper: TopFilmRemoteMapper,
    private val filmDetailsRemoteMapper: FilmDetailsRemoteMapper,
) {

    fun getTopFilmsPagingDataFlow(coroutineScope: CoroutineScope): Flow<PagingData<TopFilm>> {
        return filmsRemoteDataSource.getTopFilmsRemotePagingDataFlow().cachedIn(coroutineScope)
            .combine(
                filmsLocalDataSource.getFilmsStatusLocalListFlow()
            ) { f, s ->
                f.map { topFilmRemote ->
                    val filmStatusLocal = s.firstOrNull { it.filmId == topFilmRemote.filmId }
                    topFilmRemoteMapper.map(
                        topFilmRemote,
                        isFavourite = filmStatusLocal?.isFavourite ?: false,
                        isWatched = filmStatusLocal?.isFavourite ?: false,
                    )
                }
            }
    }

    suspend fun getFilmDetailsFlowById(filmId: Int): Flow<FilmDetails> {
        val filmStatusLocal = filmsLocalDataSource.getFilmStatusLocalFlow(filmId)
        return filmStatusLocal.map { filmLocalStatus ->
            filmDetailsRemoteMapper.map(
                filmDetailsRemote = filmsRemoteDataSource.getFilmDetailsRemoteById(filmId),
                isWatched = filmLocalStatus?.isWatched ?: false,
                isFavourite = filmLocalStatus?.isFavourite ?: false,
            )
        }
    }

    fun toggleFavouriteFilm(filmId: Int) {
        val filmStatusLocal = filmsLocalDataSource.getFilmStatusLocal(filmId)
        if (filmStatusLocal == null) {
            filmsLocalDataSource.insertFilmStatusLocal(
                FilmStatusLocal(
                    filmId = filmId,
                    isFavourite = true,
                )
            )
        } else {
            filmsLocalDataSource.updateFilmStatusLocal(
                filmStatusLocal.copy(isFavourite = !filmStatusLocal.isFavourite)
            )
        }
    }

    fun toggleWatchedFilm(filmId: Int) {
        val filmStatusLocal = filmsLocalDataSource.getFilmStatusLocal(filmId)
        if (filmStatusLocal == null) {
            filmsLocalDataSource.insertFilmStatusLocal(
                FilmStatusLocal(
                    filmId = filmId,
                    isWatched = true,
                )
            )
        } else {
            filmsLocalDataSource.updateFilmStatusLocal(
                filmStatusLocal.copy(isWatched = !filmStatusLocal.isWatched)
            )
        }
    }

    fun getFavFilms(): Flow<List<Int>> {
        return filmsLocalDataSource.getFavouriteFilmIdsListFlow()
    }
}
