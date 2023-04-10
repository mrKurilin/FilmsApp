package com.mrkurilin.filmsapp.domain.usecase

import com.mrkurilin.filmsapp.R
import com.mrkurilin.filmsapp.domain.credentialvalidation.EmailValidation
import com.mrkurilin.filmsapp.domain.credentialvalidation.PasswordValidation
import com.mrkurilin.filmsapp.domain.credentialvalidation.SignInAuthFieldWithErrorMessage
import com.mrkurilin.filmsapp.domain.exceptions.InvalidSignInCredentialException
import javax.inject.Inject

class SignInCredentialValidation @Inject constructor(
    private val emailValidation: EmailValidation,
    private val passwordValidation: PasswordValidation,
) {

    fun validate(email: String, password: String) {
        val errors = mutableListOf<SignInAuthFieldWithErrorMessage>()

        if (email.isBlank()) {
            errors.add(SignInAuthFieldWithErrorMessage.Email(R.string.field_should_not_be_empty))
        }

        if (password.isBlank()) {
            errors.add(SignInAuthFieldWithErrorMessage.Password(R.string.field_should_not_be_empty))
        }

        if (email.isNotBlank() && emailValidation.isInvalidEmail(email)) {
            errors.add(SignInAuthFieldWithErrorMessage.Email(R.string.invalid_email))
        }

        if (
            password.isNotBlank()
            &&
            passwordValidation.isInvalidPassword(password)
        ) {
            errors.add(SignInAuthFieldWithErrorMessage.Password(R.string.invalid_password))
        }

        if (errors.isNotEmpty()) {
            throw InvalidSignInCredentialException(errors)
        }
    }
}