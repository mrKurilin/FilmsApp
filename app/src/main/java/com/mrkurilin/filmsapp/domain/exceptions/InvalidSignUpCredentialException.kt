package com.mrkurilin.filmsapp.domain.exceptions

import com.mrkurilin.filmsapp.domain.credentialvalidation.SignUpAuthFieldWithErrorMessage

class InvalidSignUpCredentialException(
    val errors: List<SignUpAuthFieldWithErrorMessage>
) : Throwable()