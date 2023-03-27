package com.mrkurilin.filmsapp.presentation.signupfragment

import com.mrkurilin.filmsapp.data.EmailValidation
import com.mrkurilin.filmsapp.data.PasswordValidation
import com.mrkurilin.filmsapp.data.SignUpFieldsValidation
import com.mrkurilin.filmsapp.data.exceptions.EmptyFieldsException
import com.mrkurilin.filmsapp.data.exceptions.InvalidEmailException
import com.mrkurilin.filmsapp.data.exceptions.InvalidPasswordException
import com.mrkurilin.filmsapp.data.exceptions.PasswordsMismatchException
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class SignUpViewModelTest {

    private lateinit var signUpViewModel: SignUpViewModel
    private val validEmail = "Test@test.test"
    private val validPassword = "Kotlin123@#"
    private val validPassword2 = "Kotlin1234@#"
    private val invalidEmail = "invalidEmail"
    private val invalidPassword = "123"

    private val signUpFieldsValidation = SignUpFieldsValidation(
        EmailValidation(),
        PasswordValidation()
    )

    @Before
    fun setup() {
        signUpViewModel = SignUpViewModel(signUpFieldsValidation)
    }

    @Test
    fun `initial UI state`() {
        assertTrue(signUpViewModel.uiStateFlow.value == SignUpUIState.Initial)
    }

    @Test
    fun `empty values`() {
        signUpViewModel.tryToSignUp("", "", "")
        val currentUiState = signUpViewModel.uiStateFlow.value
        assertTrue(currentUiState is SignUpUIState.Error)
        assertTrue((currentUiState as SignUpUIState.Error).exception is EmptyFieldsException)
    }

    @Test
    fun `empty email`() {
        signUpViewModel.tryToSignUp("", validPassword, validPassword)
        val currentUiState = signUpViewModel.uiStateFlow.value
        assertTrue(currentUiState is SignUpUIState.Error)
        assertTrue((currentUiState as SignUpUIState.Error).exception is EmptyFieldsException)
    }

    @Test
    fun `empty password`() {
        signUpViewModel.tryToSignUp(validEmail, "", validPassword)
        val currentUiState = signUpViewModel.uiStateFlow.value
        if (currentUiState is SignUpUIState.Error) {
            assertTrue(currentUiState.exception is EmptyFieldsException)
        } else {
            fail("current UI state is not SignUpUIState.Error")
        }
    }

    @Test
    fun `invalid email`() {
        signUpViewModel.tryToSignUp(invalidEmail, validPassword, validPassword)
        val currentUiState = signUpViewModel.uiStateFlow.value
        if (currentUiState is SignUpUIState.Error) {
            assertTrue(currentUiState.exception is InvalidEmailException)
        } else {
            fail("current UI state is not SignUpUIState.Error")
        }
    }

    @Test
    fun `invalid password`() {
        signUpViewModel.tryToSignUp(validEmail, invalidPassword, invalidPassword)
        val currentUiState = signUpViewModel.uiStateFlow.value
        if (currentUiState is SignUpUIState.Error) {
            assertTrue(currentUiState.exception is InvalidPasswordException)
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