package com.mrkurilin.filmsapp.data.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mrkurilin.filmsapp.domain.model.Film
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PagingFilmsFlow @Inject constructor(
    filmsDataSource: FilmsDataSource,
) {

    private var pagingFilmsFlow: Flow<PagingData<Film>>

    init {
        val config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        )

        val filmsPager = Pager(
            config = config,
            pagingSourceFactory = {
                filmsDataSource
            }
        )

        pagingFilmsFlow = filmsPager.flow
    }

    fun cachedIn(viewModelScope: CoroutineScope): Flow<PagingData<Film>> {
        return pagingFilmsFlow.cachedIn(viewModelScope)
    }
}