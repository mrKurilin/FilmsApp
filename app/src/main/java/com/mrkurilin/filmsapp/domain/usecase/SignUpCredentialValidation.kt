package com.mrkurilin.filmsapp.domain.usecase

import com.mrkurilin.filmsapp.R
import com.mrkurilin.filmsapp.domain.credentialvalidation.EmailValidation
import com.mrkurilin.filmsapp.domain.credentialvalidation.PasswordValidation
import com.mrkurilin.filmsapp.domain.credentialvalidation.SignUpAuthFieldWithErrorMessage
import com.mrkurilin.filmsapp.domain.exceptions.InvalidSignUpCredentialException
import javax.inject.Inject

class SignUpCredentialValidation @Inject constructor(
    private val emailValidation: EmailValidation,
    private val passwordValidation: PasswordValidation,
) {

    fun validate(
        email: String,
        password: String,
        confirmPassword: String,
    ) {
        val errors = mutableListOf<SignUpAuthFieldWithErrorMessage>()

        if (email.isBlank()) {
            errors.add(SignUpAuthFieldWithErrorMessage.Email(R.string.field_should_not_be_empty))
        }

        if (password.isBlank()) {
            errors.add(SignUpAuthFieldWithErrorMessage.Password(R.string.field_should_not_be_empty))
        }

        if (confirmPassword.isBlank()) {
            errors.add(
                SignUpAuthFieldWithErrorMessage.ConfirmPassword(R.string.field_should_not_be_empty)
            )
        }

        if (email.isNotBlank() && emailValidation.isInvalidEmail(email)) {
            errors.add(SignUpAuthFieldWithErrorMessage.Email(R.string.invalid_email))
        }

        if (password.isNotBlank() && passwordValidation.isInvalidPassword(password)) {
            errors.add(SignUpAuthFieldWithErrorMessage.Password(R.string.invalid_password))
        }

        if (
            passwordValidation.isValidPassword(password)
            &&
            confirmPassword.isNotBlank()
            &&
            password != confirmPassword
        ) {
            errors.add(SignUpAuthFieldWithErrorMessage.ConfirmPassword(R.string.mismatch_password))
        }

        if (errors.isNotEmpty()) {
            throw InvalidSignUpCredentialException(errors)
        }
    }
}