package com.mrkurilin.filmsapp.domain.credentialvalidation

import java.util.regex.Pattern

class EmailValidation {

    private val pattern = Pattern.compile(
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    )

    fun isInvalidEmail(email: CharSequence): Boolean {
        return email.isEmpty() || !pattern.matcher(email).matches()
    }
}