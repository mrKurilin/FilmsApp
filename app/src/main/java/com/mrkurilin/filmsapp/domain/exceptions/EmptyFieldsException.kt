package com.mrkurilin.filmsapp.domain.exceptions

import com.mrkurilin.filmsapp.domain.credentialvalidation.AuthField

data class EmptyFieldsException(val emptyAuthFields: List<AuthField>) : Exception()