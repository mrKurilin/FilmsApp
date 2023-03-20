package com.mrkurilin.filmsapp.presentation.signinfragment

sealed class SignInUIState {

    object Initial : SignInUIState()

    object Loading : SignInUIState()

    object SignedIn : SignInUIState()

    class Error(val exception: Exception) : SignInUIState()
}