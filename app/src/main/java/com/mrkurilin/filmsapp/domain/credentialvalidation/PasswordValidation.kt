package com.mrkurilin.filmsapp.domain.credentialvalidation

import javax.inject.Inject

class PasswordValidation @Inject constructor() {

    fun isInvalidPassword(password: String): Boolean {
        return !isValidPassword(password)
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }
}