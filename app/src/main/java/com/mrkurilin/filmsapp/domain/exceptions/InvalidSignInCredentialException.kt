package com.mrkurilin.filmsapp.domain.exceptions

import com.mrkurilin.filmsapp.domain.credentialvalidation.SignInAuthFieldWithErrorMessage

class InvalidSignInCredentialException(
    val errors: List<SignInAuthFieldWithErrorMessage>
) : Throwable()