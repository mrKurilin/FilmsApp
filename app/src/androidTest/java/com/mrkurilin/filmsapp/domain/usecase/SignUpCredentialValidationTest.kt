package com.mrkurilin.filmsapp.domain.usecase

import com.mrkurilin.filmsapp.R
import com.mrkurilin.filmsapp.domain.credentialvalidation.EmailValidation
import com.mrkurilin.filmsapp.domain.credentialvalidation.PasswordValidation
import com.mrkurilin.filmsapp.domain.credentialvalidation.SignUpAuthFieldWithErrorMessage
import org.junit.Assert.assertEquals
import org.junit.Test

class SignUpCredentialValidationTest {

    private val signUpCredentialValidation = SignUpCredentialValidation(
        emailValidation = EmailValidation(),
        passwordValidation = PasswordValidation(),
    )

    private lateinit var expectedErrorsList: List<SignUpAuthFieldWithErrorMessage>
    private lateinit var actualErrorsList: List<SignUpAuthFieldWithErrorMessage>

    private val validEmail = "test@example.com"
    private val inValidEmail = "invalidEmail"
    private val validPassword = "validPassword1"
    private val otherValidPassword = "validPassword2"
    private val invalidPassword = "123"

    @Test
    fun getValidationErrorsShouldReturnEmptyListForValidEmailPasswordAndConfirmPassword() {
        expectedErrorsList = listOf()

        actualErrorsList = signUpCredentialValidation.getValidationErrors(
            email = validEmail,
            password = validPassword,
            confirmPassword = validPassword,
        )

        assertEquals(expectedErrorsList, actualErrorsList)
    }

    @Test
    fun getValidationErrorsShouldReturnErrorListForBlankEmailPasswordAndConfirmPassword() {
        expectedErrorsList = listOf(
            SignUpAuthFieldWithErrorMessage.Email(R.string.field_should_not_be_empty),
            SignUpAuthFieldWithErrorMessage.Password(R.string.field_should_not_be_empty),
            SignUpAuthFieldWithErrorMessage.ConfirmPassword(R.string.field_should_not_be_empty),
        )

        actualErrorsList = signUpCredentialValidation.getValidationErrors(
            email = "",
            password = "",
            confirmPassword = "",
        )

        assertEquals(expectedErrorsList, actualErrorsList)
    }

    @Test
    fun getValidationErrorsShouldReturnErrorListForInvalidEmail() {
        expectedErrorsList = listOf(
            SignUpAuthFieldWithErrorMessage.Email(R.string.invalid_email),
        )

        actualErrorsList = signUpCredentialValidation.getValidationErrors(
            email = inValidEmail,
            password = validPassword,
            confirmPassword = validPassword,
        )

        assertEquals(expectedErrorsList, actualErrorsList)
    }

    @Test
    fun getValidationErrorsShouldReturnErrorListForInvalidPassword() {
        expectedErrorsList = listOf(
            SignUpAuthFieldWithErrorMessage.Password(R.string.invalid_password),
        )

        actualErrorsList = signUpCredentialValidation.getValidationErrors(
            email = validEmail,
            password = invalidPassword,
            confirmPassword = invalidPassword,
        )

        assertEquals(expectedErrorsList, actualErrorsList)
    }

    @Test
    fun getValidationErrorsShouldReturnErrorListForMismatchedPassword() {
        expectedErrorsList = listOf(
            SignUpAuthFieldWithErrorMessage.ConfirmPassword(R.string.mismatch_password),
        )

        actualErrorsList = signUpCredentialValidation.getValidationErrors(
            email = validEmail,
            password = validPassword,
            confirmPassword = otherValidPassword,
        )

        assertEquals(expectedErrorsList, actualErrorsList)
    }

    @Test
    fun getValidationErrorsShouldReturnErrorListForMismatchedPasswordAndInvalidEmail() {
        expectedErrorsList = listOf(
            SignUpAuthFieldWithErrorMessage.Email(R.string.invalid_email),
            SignUpAuthFieldWithErrorMessage.ConfirmPassword(R.string.mismatch_password),
        )

        actualErrorsList = signUpCredentialValidation.getValidationErrors(
            email = inValidEmail,
            password = validPassword,
            confirmPassword = otherValidPassword,
        )

        assertEquals(expectedErrorsList, actualErrorsList)
    }
}