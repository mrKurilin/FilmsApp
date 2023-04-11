package com.mrkurilin.filmsapp.domain.usecase

import com.mrkurilin.filmsapp.R
import com.mrkurilin.filmsapp.domain.credentialvalidation.EmailValidation
import com.mrkurilin.filmsapp.domain.credentialvalidation.PasswordValidation
import com.mrkurilin.filmsapp.domain.credentialvalidation.SignInAuthFieldWithErrorMessage
import javax.inject.Inject

class SignInCredentialValidation @Inject constructor(
    private val emailValidation: EmailValidation,
    private val passwordValidation: PasswordValidation,
) {

    fun getValidationErrors(
        email: String,
        password: String,
    ): List<SignInAuthFieldWithErrorMessage> {
        val errors = mutableListOf<SignInAuthFieldWithErrorMessage>()

        if (email.isBlank()) {
            errors.add(
                SignInAuthFieldWithErrorMessage.Email(
                    messageRes = R.string.field_should_not_be_empty,
                )
            )
        } else if (emailValidation.isInvalidEmail(email)) {
            errors.add(
                SignInAuthFieldWithErrorMessage.Email(
                    messageRes = R.string.invalid_email,
                )
            )
        }

        if (password.isBlank()) {
            errors.add(
                SignInAuthFieldWithErrorMessage.Password(
                    messageRes = R.string.field_should_not_be_empty,
                )
            )
        } else if (passwordValidation.isInvalidPassword(password)) {
            errors.add(
                SignInAuthFieldWithErrorMessage.Password(
                    messageRes = R.string.invalid_password,
                )
            )
        }

        return errors
    }
}