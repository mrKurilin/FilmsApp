package com.mrkurilin.filmsapp.presentation.signinfragment

import com.mrkurilin.filmsapp.data.EmailValidation
import com.mrkurilin.filmsapp.data.PasswordValidation
import com.mrkurilin.filmsapp.data.SignInFieldsValidation
import com.mrkurilin.filmsapp.data.exceptions.EmptyFieldsException
import com.mrkurilin.filmsapp.data.exceptions.InvalidEmailException
import com.mrkurilin.filmsapp.data.exceptions.InvalidPasswordException
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class SignInViewModelTest {

    private lateinit var signInViewModel: SignInViewModel
    private val validEmail = "Test@test.test"
    private val validPassword = "Qq12345678"
    private val invalidEmail = "invalidEmail"
    private val invalidPassword = "123"

    private val signInFieldsValidation = SignInFieldsValidation(
        EmailValidation(),
        PasswordValidation()
    )

    @Before
    fun setup() {
        signInViewModel = SignInViewModel(signInFieldsValidation)
    }

    @Test
    fun `initial UI state`() {
        assertTrue(signInViewModel.uiStateFlow.value == SignInUIState.Initial)
    }

    @Test
    fun `empty values`() {
        signInViewModel.tryToSignIn("", "")
        val currentUiState = signInViewModel.uiStateFlow.value
        if (currentUiState is SignInUIState.Error) {
            assertTrue(currentUiState.exception is EmptyFieldsException)
        } else {
            fail("Current UI State is not SignInUIState.Error")
        }
    }

    @Test
    fun `empty email`() {
        signInViewModel.tryToSignIn("", validPassword)
        val currentUiState = signInViewModel.uiStateFlow.value
        if (currentUiState is SignInUIState.Error) {
            assertTrue(currentUiState.exception is EmptyFieldsException)
        } else {
            fail("Current UI State is not SignInUIState.Error")
        }
    }

    @Test
    fun `empty password`() {
        signInViewModel.tryToSignIn(validEmail, "")
        val currentUiState = signInViewModel.uiStateFlow.value
        if (currentUiState is SignInUIState.Error) {
            assertTrue(currentUiState.exception is EmptyFieldsException)
        } else {
            fail("Current UI State is not SignInUIState.Error")
        }
    }

    @Test
    fun `invalid email`() {
        signInViewModel.tryToSignIn(invalidEmail, validPassword)
        val currentUiState = signInViewModel.uiStateFlow.value
        if (currentUiState is SignInUIState.Error) {
            assertTrue(currentUiState.exception is InvalidEmailException)
        } else {
            fail("Current UI State is not SignInUIState.Error")
        }
    }

    @Test
    fun `invalid password`() {
        signInViewModel.tryToSignIn(validEmail, invalidPassword)
        val currentUiState = signInViewModel.uiStateFlow.value
        if (currentUiState is SignInUIState.Error) {
            assertTrue(currentUiState.exception is InvalidPasswordException)
        } else {
            fail("Current UI State is not SignInUIState.Error")
        }
    }
}