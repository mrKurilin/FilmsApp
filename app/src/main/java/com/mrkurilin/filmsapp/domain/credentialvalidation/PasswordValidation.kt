package com.mrkurilin.filmsapp.domain.credentialvalidation

import java.util.regex.Pattern
import javax.inject.Inject

class PasswordValidation @Inject constructor() {

    private val passwordRegex = Pattern.compile(
        "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=\\S+$)" +           //no white spaces
                ".{8,}" +               //at least 8 characters
                "$"
    )

    fun isInvalidPassword(password: String): Boolean {
        return !passwordRegex.matcher(password).matches()
    }
}