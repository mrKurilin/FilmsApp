package com.mrkurilin.filmsapp.presentation.signinfragment

import com.mrkurilin.filmsapp.domain.credentialvalidation.SignInAuthFieldWithErrorMessage

sealed class SignInUIState {

    object Initial : SignInUIState()

    object Loading : SignInUIState()

    object SignedIn : SignInUIState()

    class Error(val exception: Throwable) : SignInUIState()

    class ValidationError(
        val signInAuthFieldsWithErrorMessage: List<SignInAuthFieldWithErrorMessage>
    ) : SignInUIState()
}