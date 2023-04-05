package com.mrkurilin.filmsapp.domain.exceptions

import com.mrkurilin.filmsapp.domain.credentialvalidation.AuthField

class EmptyFieldsException(val emptyAuthFields: List<AuthField>) : Exception() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as EmptyFieldsException
        if (emptyAuthFields != other.emptyAuthFields) return false
        return true
    }

    override fun hashCode(): Int {
        return emptyAuthFields.hashCode()
    }
}