package com.mrkurilin.filmsapp.presentation.topfilmsfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.mrkurilin.filmsapp.data.network.PagingFilmsFlow
import com.mrkurilin.filmsapp.domain.model.Film
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TopFilmsViewModel @Inject constructor(
    pagingFilmFlow: PagingFilmsFlow,
) : ViewModel() {

    val pagingFilms: Flow<PagingData<Film>> = pagingFilmFlow.cachedIn(viewModelScope)

    fun entryFavourite(film: Film) {

    }
}