package com.mrkurilin.filmsapp.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mrkurilin.filmsapp.data.room.FavouriteFilmsDataBase
import com.mrkurilin.filmsapp.data.room.WatchedFilmsDataBase
import com.mrkurilin.filmsapp.domain.model.Film
import javax.inject.Inject

class FilmsDataSource @Inject constructor(
    private val kinopoiskApi: KinopoiskApiService,
    private val watchedFilmsDataBase: WatchedFilmsDataBase,
    private val favouriteFilmsDataBase: FavouriteFilmsDataBase,
) : PagingSource<Int, Film>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        return try {
            val currentPage = params.key ?: 1
            val response = kinopoiskApi.getTopFilms(page = currentPage)
            val films = response.films.map { filmFromNetwork ->
                Film(
                    filmId = filmFromNetwork.filmId,
                    name = filmFromNetwork.nameRu,
                    countries = filmFromNetwork.countries.joinToString(", "),
                    genres = filmFromNetwork.genres.joinToString(", "),
                    year = filmFromNetwork.year,
                    posterUrl = filmFromNetwork.posterUrl,
                    description = "",
                    isFavourite = favouriteFilmsDataBase.isFilmFavourite(
                        filmFromNetwork.filmId
                    ),
                    isWatched = watchedFilmsDataBase.isFilmWatched(filmFromNetwork.filmId),
                )
            }
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
