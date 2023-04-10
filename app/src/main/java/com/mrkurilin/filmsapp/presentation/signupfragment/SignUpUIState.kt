package com.mrkurilin.filmsapp.presentation.signupfragment

import com.mrkurilin.filmsapp.domain.credentialvalidation.SignUpAuthFieldWithErrorMessage

sealed class SignUpUIState {

    object Initial : SignUpUIState()

    object Loading : SignUpUIState()

    object SignedUp : SignUpUIState()

    data class Error(val exception: Throwable) : SignUpUIState()

    data class ValidationError(
        val signUpAuthFieldsWithErrorMessage: List<SignUpAuthFieldWithErrorMessage>
    ) : SignUpUIState()
}