package com.mrkurilin.filmsapp.presentation.filmdetailsfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrkurilin.filmsapp.data.network.KinopoiskApiService
import com.mrkurilin.filmsapp.data.room.FavouriteFilmsDataBase
import com.mrkurilin.filmsapp.data.room.WatchedFilmsDataBase
import com.mrkurilin.filmsapp.domain.model.Film
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FilmDetailsViewModel @Inject constructor(
    private val kinopoiskApiService: KinopoiskApiService,
    private val watchedFilmsDataBase: WatchedFilmsDataBase,
    private val favouriteFilmsDataBase: FavouriteFilmsDataBase,
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<FilmDetailsUIState>(FilmDetailsUIState.Loading)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    fun loadFilm(filmId: Int) {
        viewModelScope.launch {
            val kinopoiskFilm =
                withContext(Dispatchers.Default) {
                    kinopoiskApiService.getKinopoiskFilm(id = filmId)
                }
            _uiStateFlow.value = FilmDetailsUIState.FilmLoaded(
                Film(
                    filmId = kinopoiskFilm.kinopoiskId,
                    name = kinopoiskFilm.nameRu ?: kinopoiskFilm.nameEn ?: "",
                    countries = kinopoiskFilm.countries?.joinToString(", ") ?: "",
                    genres = kinopoiskFilm.genres?.joinToString(", ") ?: "",
                    year = kinopoiskFilm.year,
                    posterUrl = kinopoiskFilm.posterUrl,
                    description = kinopoiskFilm.description ?: kinopoiskFilm.shortDescription ?: "",
                    isFavourite = favouriteFilmsDataBase.isFilmFavourite(
                        kinopoiskFilm.kinopoiskId
                    ),
                    isWatched = watchedFilmsDataBase.isFilmWatched(kinopoiskFilm.kinopoiskId),
                )
            )
        }
    }
}