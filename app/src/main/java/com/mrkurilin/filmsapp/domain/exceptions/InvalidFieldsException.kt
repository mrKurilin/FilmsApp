package com.mrkurilin.filmsapp.domain.exceptions

import com.mrkurilin.filmsapp.domain.credentialvalidation.AuthField

class InvalidFieldsException(val invalidAuthFields: List<AuthField>) : Exception() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as InvalidFieldsException
        if (invalidAuthFields != other.invalidAuthFields) return false
        return true
    }

    override fun hashCode(): Int {
        return invalidAuthFields.hashCode()
    }
}