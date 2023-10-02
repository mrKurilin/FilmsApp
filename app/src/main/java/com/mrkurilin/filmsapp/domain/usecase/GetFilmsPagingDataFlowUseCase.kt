package com.mrkurilin.filmsapp.domain.usecase

import androidx.paging.PagingData
import com.mrkurilin.filmsapp.data.FilmsRepository
import com.mrkurilin.filmsapp.domain.model.TopFilm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFilmsPagingDataFlowUseCase @Inject constructor(
    private val filmsRepository: FilmsRepository
) {

    fun get(viewModelScope: CoroutineScope): Flow<PagingData<TopFilm>> {
        return filmsRepository.getTopFilmsPagingDataFlow(viewModelScope)
    }
}