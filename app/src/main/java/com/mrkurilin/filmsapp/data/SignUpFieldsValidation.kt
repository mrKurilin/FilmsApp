package com.mrkurilin.filmsapp.data

import com.mrkurilin.filmsapp.data.exceptions.EmptyFieldsException
import com.mrkurilin.filmsapp.data.exceptions.InvalidEmailException
import com.mrkurilin.filmsapp.data.exceptions.InvalidPasswordException
import com.mrkurilin.filmsapp.data.exceptions.PasswordsMismatchException

class SignUpFieldsValidation(
    private val emailValidation: EmailValidation,
    private val passwordValidation: PasswordValidation,
) {

    fun validateSignUpFields(
        email: String,
        password: String,
        passwordConfirmation: String,
    ) {
        when {
            email.isEmpty() || password.isEmpty() || passwordConfirmation.isEmpty() -> {
                throw EmptyFieldsException()
            }
            emailValidation.isInvalidEmail(email) -> {
                throw InvalidEmailException()
            }
            passwordValidation.isInvalidPassword(password) -> {
                throw InvalidPasswordException()
            }
            password != passwordConfirmation -> {
                throw PasswordsMismatchException()
            }
        }
    }
}