package com.mrkurilin.filmsapp.presentation.signinfragment

import com.mrkurilin.filmsapp.data.EmailValidation
import com.mrkurilin.filmsapp.data.PasswordValidation
import com.mrkurilin.filmsapp.data.exceptions.EmptyFieldsException
import com.mrkurilin.filmsapp.data.exceptions.InvalidEmailException
import com.mrkurilin.filmsapp.data.exceptions.InvalidPasswordException
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SignInViewModelTest {

    private lateinit var signInViewModel: SignInViewModel
    private val validEmail = "Test@test.test"
    private val validPassword = "Test@test.test"
    private val invalidEmail = "invalidEmail"
    private val invalidPassword = "123"

    @Before
    fun setup() {
        signInViewModel = SignInViewModel(EmailValidation(), PasswordValidation())
    }

    @Test
    fun `initial UI state`() {
        assertTrue(signInViewModel.uiStateFlow.value == SignInUIState.Initial)
    }

    @Test
    fun `empty values`() {
        signInViewModel.signInButtonPressed("", "")
        val currentUiState = signInViewModel.uiStateFlow.value
        assertTrue(currentUiState is SignInUIState.Error)
        assertTrue((currentUiState as SignInUIState.Error).exception is EmptyFieldsException)
    }

    @Test
    fun `empty email`() {
        signInViewModel.signInButtonPressed("", validPassword)
        val currentUiState = signInViewModel.uiStateFlow.value
        assertTrue(currentUiState is SignInUIState.Error)
        assertTrue((currentUiState as SignInUIState.Error).exception is EmptyFieldsException)
    }

    @Test
    fun `empty password`() {
        signInViewModel.signInButtonPressed(validEmail, "")
        val currentUiState = signInViewModel.uiStateFlow.value
        assertTrue(currentUiState is SignInUIState.Error)
        assertTrue((currentUiState as SignInUIState.Error).exception is EmptyFieldsException)
    }

    @Test
    fun `invalid email`() {
        signInViewModel.signInButtonPressed(invalidEmail, validPassword)
        val currentUiState = signInViewModel.uiStateFlow.value
        assertTrue(currentUiState is SignInUIState.Error)
        assertTrue((currentUiState as SignInUIState.Error).exception is InvalidEmailException)
    }

    @Test
    fun `invalid password`() {
        signInViewModel.signInButtonPressed(validEmail, invalidPassword)
        val currentUiState = signInViewModel.uiStateFlow.value
        assertTrue((currentUiState as SignInUIState.Error).exception is InvalidPasswordException)
    }
}