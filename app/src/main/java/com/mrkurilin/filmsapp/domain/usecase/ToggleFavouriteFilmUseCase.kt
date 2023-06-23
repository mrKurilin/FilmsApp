package com.mrkurilin.filmsapp.domain.usecase

import com.mrkurilin.filmsapp.data.FilmsRepository
import javax.inject.Inject

class ToggleFavouriteFilmUseCase @Inject constructor(
    private val filmsRepository: FilmsRepository,
) {

    fun toggle(filmId: Int) {
        filmsRepository.toggleFavouriteFilm(filmId)
    }
}