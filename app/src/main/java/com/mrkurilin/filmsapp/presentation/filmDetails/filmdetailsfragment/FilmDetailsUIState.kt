package com.mrkurilin.filmsapp.presentation.filmDetails.filmdetailsfragment

import com.mrkurilin.filmsapp.presentation.filmDetails.model.FilmDetailsUiModel

sealed class FilmDetailsUIState {

    object Loading : FilmDetailsUIState()

    data class FilmLoaded(val filmDetailsUiModel: FilmDetailsUiModel) : FilmDetailsUIState()

    object Error : FilmDetailsUIState()
}