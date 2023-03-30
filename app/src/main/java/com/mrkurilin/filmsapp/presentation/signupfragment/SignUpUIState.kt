package com.mrkurilin.filmsapp.presentation.signupfragment

sealed class SignUpUIState {

    object Initial : SignUpUIState()

    object Loading : SignUpUIState()

    object SignedUp : SignUpUIState()

    data class Error(val exception: Throwable) : SignUpUIState()
}