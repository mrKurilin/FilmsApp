package com.mrkurilin.filmsapp.presentation.signinfragment

import com.mrkurilin.filmsapp.MainDispatcherRule
import com.mrkurilin.filmsapp.domain.credentialvalidation.AuthField
import com.mrkurilin.filmsapp.domain.credentialvalidation.EmailValidation
import com.mrkurilin.filmsapp.domain.credentialvalidation.PasswordValidation
import com.mrkurilin.filmsapp.domain.credentialvalidation.SignInUser
import com.mrkurilin.filmsapp.domain.exceptions.EmptyFieldsException
import com.mrkurilin.filmsapp.domain.exceptions.InvalidFieldsException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SignInViewModelTest {

    private lateinit var signInViewModel: SignInViewModel
    private val validEmail = "Test@test.test"
    private val validPassword = "Qq12345678"
    private val invalidEmail = "invalidEmail"
    private val invalidPassword = "123"

    private val signInUser = SignInUser(
        EmailValidation(),
        PasswordValidation()
    )

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        signInViewModel = SignInViewModel(signInUser)
    }

    @Test
    fun `initial UI state`() {
        assertTrue(signInViewModel.uiStateFlow.value == SignInUIState.Initial)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `empty values`() = runTest {
        signInViewModel.tryToSignIn("", "")

        val currentUiState = signInViewModel.uiStateFlow.value

        if (currentUiState is SignInUIState.Error) {
            val expected = EmptyFieldsException(listOf(AuthField.Email, AuthField.Password))
            assertEquals(expected, currentUiState.exception)
        } else {
            fail("Current UI State is not SignInUIState.Error")
        }
    }

    @Test
    fun `empty email`() {
        signInViewModel.tryToSignIn("", validPassword)

        val currentUiState = signInViewModel.uiStateFlow.value

        if (currentUiState is SignInUIState.Error) {
            val expected = EmptyFieldsException(listOf(AuthField.Email))
            assertEquals(expected, currentUiState.exception)
        } else {
            fail("Current UI State is not SignInUIState.Error")
        }
    }

    @Test
    fun `empty password`() {
        signInViewModel.tryToSignIn(validEmail, "")

        val currentUiState = signInViewModel.uiStateFlow.value

        if (currentUiState is SignInUIState.Error) {
            val expected = EmptyFieldsException(listOf(AuthField.Password))
            assertEquals(expected, currentUiState.exception)
        } else {
            fail("Current UI State is not SignInUIState.Error")
        }
    }

    @Test
    fun `invalid email`() {
        signInViewModel.tryToSignIn(invalidEmail, validPassword)
        val currentUiState = signInViewModel.uiStateFlow.value
        if (currentUiState is SignInUIState.Error) {
            val expected = InvalidFieldsException(listOf(AuthField.Email))
            assertEquals(expected, currentUiState.exception)
        } else {
            fail("Current UI State is not SignInUIState.Error")
        }
    }

    @Test
    fun `invalid password`() {
        signInViewModel.tryToSignIn(validEmail, invalidPassword)
        val currentUiState = signInViewModel.uiStateFlow.value
        if (currentUiState is SignInUIState.Error) {
            val expected = InvalidFieldsException(listOf(AuthField.Password))
            assertTrue(currentUiState.exception == expected)
        } else {
            fail("Current UI State is not SignInUIState.Error")
        }
    }
}