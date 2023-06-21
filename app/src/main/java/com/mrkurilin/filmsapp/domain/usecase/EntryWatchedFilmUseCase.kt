package com.mrkurilin.filmsapp.domain.usecase

import com.mrkurilin.filmsapp.data.FilmsRepository
import javax.inject.Inject

class EntryWatchedFilmUseCase @Inject constructor(
    private val filmsRepository: FilmsRepository
) {

    fun entry(filmId: Int) {
        filmsRepository.entryWatchedFilm(filmId)
    }
}