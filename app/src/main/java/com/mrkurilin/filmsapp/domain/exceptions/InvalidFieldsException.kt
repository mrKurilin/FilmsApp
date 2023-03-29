package com.mrkurilin.filmsapp.domain.exceptions

import com.mrkurilin.filmsapp.domain.credentialvalidation.AuthField

data class InvalidFieldsException(val invalidFields: List<AuthField>) : Exception()