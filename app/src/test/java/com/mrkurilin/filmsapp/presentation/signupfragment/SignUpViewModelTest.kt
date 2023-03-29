package com.mrkurilin.filmsapp.presentation.signupfragment

import com.mrkurilin.filmsapp.MainDispatcherRule
import com.mrkurilin.filmsapp.domain.credentialvalidation.AuthField
import com.mrkurilin.filmsapp.domain.credentialvalidation.EmailValidation
import com.mrkurilin.filmsapp.domain.credentialvalidation.PasswordValidation
import com.mrkurilin.filmsapp.domain.credentialvalidation.SignUpUser
import com.mrkurilin.filmsapp.domain.exceptions.EmptyFieldsException
import com.mrkurilin.filmsapp.domain.exceptions.InvalidFieldsException
import com.mrkurilin.filmsapp.domain.exceptions.PasswordsMismatchException
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SignUpViewModelTest {

    private lateinit var signUpViewModel: SignUpViewModel
    private val validEmail = "Test@test.test"
    private val validPassword = "Kotlin123@#"
    private val validPassword2 = "Kotlin1234@#"
    private val invalidEmail = "invalidEmail"
    private val invalidPassword = "123"

    private val signUpUser = SignUpUser(
        EmailValidation(),
        PasswordValidation()
    )

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        signUpViewModel = SignUpViewModel(signUpUser)
    }

    @Test
    fun `initial UI state`() {
        assertTrue(signUpViewModel.uiStateFlow.value == SignUpUIState.Initial)
    }

    @Test
    fun `empty values`() {
        signUpViewModel.tryToSignUp("", "", "")

        val currentUiState = signUpViewModel.uiStateFlow.value

        if (currentUiState is SignUpUIState.Error) {
            val expected = EmptyFieldsException(
                listOf(
                    AuthField.Email,
                    AuthField.Password,
                    AuthField.ConfirmPassword
                )
            )
            assertEquals(expected, currentUiState.exception)
        } else {
            fail("current UI state is not SignUpUIState.Error")
        }
    }

    @Test
    fun `empty email`() {
        signUpViewModel.tryToSignUp("", validPassword, validPassword)

        val currentUiState = signUpViewModel.uiStateFlow.value

        if (currentUiState is SignUpUIState.Error) {
            val expected = EmptyFieldsException(listOf(AuthField.Email))
            assertEquals(expected, currentUiState.exception)
        } else {
            fail("current UI state is not SignUpUIState.Error")
        }
    }

    @Test
    fun `empty password`() {
        signUpViewModel.tryToSignUp(validEmail, "", validPassword)
        val currentUiState = signUpViewModel.uiStateFlow.value
        if (currentUiState is SignUpUIState.Error) {
            val expected = EmptyFieldsException(listOf(AuthField.Password))
            assertEquals(expected, currentUiState.exception)
        } else {
            fail("current UI state is not SignUpUIState.Error")
        }
    }

    @Test
    fun `invalid email`() {
        signUpViewModel.tryToSignUp(invalidEmail, validPassword, validPassword)
        val currentUiState = signUpViewModel.uiStateFlow.value
        if (currentUiState is SignUpUIState.Error) {
            val expected = InvalidFieldsException(listOf(AuthField.Email))
            assertEquals(expected, currentUiState.exception)
        } else {
            fail("current UI state is not SignUpUIState.Error")
        }
    }

    @Test
    fun `invalid password`() {
        signUpViewModel.tryToSignUp(validEmail, invalidPassword, invalidPassword)
        val currentUiState = signUpViewModel.uiStateFlow.value
        if (currentUiState is SignUpUIState.Error) {
            val expected = InvalidFieldsException(listOf(AuthField.Password))
            assertEquals(expected, currentUiState.exception)
        } else {
            fail("current UI state is not SignUpUIState.Error")
        }
    }

    @Test
    fun `passwords mismatch`() {
        signUpViewModel.tryToSignUp(validEmail, validPassword, validPassword2)
        val currentUiState = signUpViewModel.uiStateFlow.value
        if (currentUiState is SignUpUIState.Error) {
            assertTrue(currentUiState.exception is PasswordsMismatchException)
        } else {
            fail("current UI state is not SignUpUIState.Error")
        }
    }
}