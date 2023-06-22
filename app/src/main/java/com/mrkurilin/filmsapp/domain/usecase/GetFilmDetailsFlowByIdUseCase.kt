package com.mrkurilin.filmsapp.domain.usecase

import com.mrkurilin.filmsapp.data.FilmsRepository
import com.mrkurilin.filmsapp.domain.model.FilmDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFilmDetailsFlowByIdUseCase @Inject constructor(
    private val filmsRepository: FilmsRepository
) {

    suspend fun get(filmId: Int): Flow<FilmDetails> {
        return filmsRepository.getFilmDetailsFlowById(filmId)
    }
}