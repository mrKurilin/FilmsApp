package com.mrkurilin.filmsapp.domain.usecase

import com.mrkurilin.filmsapp.R
import com.mrkurilin.filmsapp.domain.credentialvalidation.EmailValidation
import com.mrkurilin.filmsapp.domain.credentialvalidation.PasswordValidation
import com.mrkurilin.filmsapp.domain.credentialvalidation.SignInAuthFieldWithErrorMessage
import org.junit.Assert.assertEquals
import org.junit.Test

class SignInCredentialValidationTest {

    private val signInCredentialValidation = SignInCredentialValidation(
        emailValidation = EmailValidation(),
        passwordValidation = PasswordValidation()
    )

    private lateinit var expectedErrorsList: List<SignInAuthFieldWithErrorMessage>
    private lateinit var actualErrorsList: List<SignInAuthFieldWithErrorMessage>

    private val validEmail = "test@example.com"
    private val inValidEmail = "invalidEmail"
    private val validPassword = "validPassword1"
    private val invalidPassword = "123"

    @Test
    fun getValidationErrorsShouldReturnEmptyListWhenEmailAndPasswordAreValid() {
        actualErrorsList = signInCredentialValidation.getValidationErrors(
            email = validEmail,
            password = validPassword,
        )
        expectedErrorsList = listOf()

        assertEquals(expectedErrorsList, actualErrorsList)
    }

    @Test
    fun getValidationErrorsShouldReturnErrorForEmptyEmail() {
        expectedErrorsList = listOf(
            SignInAuthFieldWithErrorMessage.Email(R.string.field_should_not_be_empty)
        )
        actualErrorsList = signInCredentialValidation.getValidationErrors(
            email = "",
            password = validPassword,
        )

        assertEquals(expectedErrorsList, actualErrorsList)
    }

    @Test
    fun getValidationErrorsShouldReturnErrorForInvalidEmail() {
        expectedErrorsList = listOf(
            SignInAuthFieldWithErrorMessage.Email(R.string.invalid_email),
        )
        actualErrorsList = signInCredentialValidation.getValidationErrors(
            email = inValidEmail,
            password = validPassword,
        )

        assertEquals(expectedErrorsList, actualErrorsList)
    }

    @Test
    fun getValidationErrorsShouldReturnErrorForEmptyPassword() {
        expectedErrorsList = listOf(
            SignInAuthFieldWithErrorMessage.Password(R.string.field_should_not_be_empty),
        )
        actualErrorsList = signInCredentialValidation.getValidationErrors(
            email = validEmail,
            password = "",
        )

        assertEquals(expectedErrorsList, actualErrorsList)
    }

    @Test
    fun getValidationErrorsShouldReturnErrorForInvalidPassword() {
        expectedErrorsList = listOf(
            SignInAuthFieldWithErrorMessage.Password(R.string.invalid_password),
        )
        actualErrorsList = signInCredentialValidation.getValidationErrors(
            email = validEmail,
            password = invalidPassword,
        )

        assertEquals(expectedErrorsList, actualErrorsList)
    }

    @Test
    fun getValidationErrorsShouldReturnMultipleErrorsForBothInvalidEmailAndPassword() {
        expectedErrorsList = listOf(
            SignInAuthFieldWithErrorMessage.Email(R.string.invalid_email),
            SignInAuthFieldWithErrorMessage.Password(R.string.invalid_password),
        )

        actualErrorsList = signInCredentialValidation.getValidationErrors(
            email = inValidEmail,
            password = invalidPassword
        )

        assertEquals(expectedErrorsList, actualErrorsList)
    }
}