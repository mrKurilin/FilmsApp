package com.mrkurilin.filmsapp.domain.usecase

import com.mrkurilin.filmsapp.R
import com.mrkurilin.filmsapp.domain.credentialvalidation.EmailValidation
import com.mrkurilin.filmsapp.domain.credentialvalidation.PasswordValidation
import com.mrkurilin.filmsapp.domain.credentialvalidation.SignUpAuthFieldWithErrorMessage
import javax.inject.Inject

class SignUpCredentialValidation @Inject constructor(
    private val emailValidation: EmailValidation,
    private val passwordValidation: PasswordValidation,
) {

    fun getValidationErrors(
        email: String,
        password: String,
        confirmPassword: String,
    ): List<SignUpAuthFieldWithErrorMessage> {
        val errors = mutableListOf<SignUpAuthFieldWithErrorMessage>()

        if (email.isBlank()) {
            errors.add(
                SignUpAuthFieldWithErrorMessage.Email(
                    messageRes = R.string.field_should_not_be_empty
                )
            )
        } else if (emailValidation.isInvalidEmail(email)) {
            errors.add(
                SignUpAuthFieldWithErrorMessage.Email(
                    messageRes = R.string.invalid_email
                )
            )
        }

        if (password.isBlank()) {
            errors.add(
                SignUpAuthFieldWithErrorMessage.Password(
                    messageRes = R.string.field_should_not_be_empty
                )
            )
        } else if (passwordValidation.isInvalidPassword(password)) {
            errors.add(
                SignUpAuthFieldWithErrorMessage.Password(
                    messageRes = R.string.invalid_password
                )
            )
        }

        if (confirmPassword.isBlank()) {
            errors.add(
                SignUpAuthFieldWithErrorMessage.ConfirmPassword(
                    messageRes = R.string.field_should_not_be_empty
                )
            )
        } else if (passwordValidation.isValidPassword(password) && password != confirmPassword) {
            errors.add(
                SignUpAuthFieldWithErrorMessage.ConfirmPassword(
                    messageRes = R.string.mismatch_password
                )
            )
        }

        return errors
    }
}