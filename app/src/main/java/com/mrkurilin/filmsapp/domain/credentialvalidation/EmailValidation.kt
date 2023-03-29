package com.mrkurilin.filmsapp.domain.credentialvalidation

import java.util.regex.Pattern

class EmailValidation {

    private val pattern = Pattern.compile(
        "[a-zA-Z0-9+._%\\-+]{1,256}" +
                "@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    fun isInvalidEmail(email: CharSequence): Boolean {
        return email.isEmpty() || !pattern.matcher(email).matches()
    }
}