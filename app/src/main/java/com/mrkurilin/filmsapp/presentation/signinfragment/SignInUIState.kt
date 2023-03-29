package com.mrkurilin.filmsapp.presentation.signinfragment

sealed class SignInUIState {

    object Initial : SignInUIState()

    object Loading : SignInUIState()

    object SignedIn : SignInUIState()

    data class Error(val exception: Throwable) : SignInUIState()
}