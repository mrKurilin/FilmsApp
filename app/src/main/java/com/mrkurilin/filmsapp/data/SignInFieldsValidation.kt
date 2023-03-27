package com.mrkurilin.filmsapp.data

import com.mrkurilin.filmsapp.data.exceptions.EmptyFieldsException
import com.mrkurilin.filmsapp.data.exceptions.InvalidEmailException
import com.mrkurilin.filmsapp.data.exceptions.InvalidPasswordException

class SignInFieldsValidation(
    private val emailValidation: EmailValidation,
    private val passwordValidation: PasswordValidation,
) {

    fun validateSignInFields(email: String, password: String) {
        when {
            email.isEmpty() || password.isEmpty() -> throw EmptyFieldsException()
            emailValidation.isInvalidEmail(email) -> throw InvalidEmailException()
            passwordValidation.isInvalidPassword(password) -> throw InvalidPasswordException()
        }
    }
}