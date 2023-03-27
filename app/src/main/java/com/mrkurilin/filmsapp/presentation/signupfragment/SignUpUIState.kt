package com.mrkurilin.filmsapp.presentation.signupfragment

sealed class SignUpUIState {

    object Initial : SignUpUIState()

    object Loading : SignUpUIState()

    object SignedUp : SignUpUIState()

    class Error(val exception: Exception) : SignUpUIState()
}