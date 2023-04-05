package com.mrkurilin.filmsapp.presentation.signupfragment

import com.mrkurilin.filmsapp.MainDispatcherRule
import com.mrkurilin.filmsapp.domain.credentialvalidation.AuthField
import com.mrkurilin.filmsapp.domain.credentialvalidation.EmailValidation
import com.mrkurilin.filmsapp.domain.credentialvalidation.PasswordValidation
import com.mrkurilin.filmsapp.domain.credentialvalidation.SignUpUser
import com.mrkurilin.filmsapp.domain.exceptions.EmptyFieldsException
import com.mrkurilin.filmsapp.domain.exceptions.InvalidFieldsException
import com.mrkurilin.filmsapp.domain.exceptions.PasswordsMismatchException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SignUpViewModelTest {

    private lateinit var signUpViewModel: SignUpViewModel
    private val validEmail = "Test@test.test"
    private val validPassword = "Kotlin123@#"
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
        assertEquals(SignUpUIState.Initial, signUpViewModel.uiStateFlow.value)
    }

    @Test
    fun `empty values`() {
        signUpViewModel.tryToSignUp("", "", "")

        val currentUiState = signUpViewModel.uiStateFlow.value

        val expected = EmptyFieldsException(
            listOf(AuthField.Email, AuthField.Password, AuthField.ConfirmPassword)
        )
        val actual = (currentUiState as SignUpUIState.Error).exception
        assertEquals(expected, actual)
    }

    @Test
    fun `empty email`() {
        signUpViewModel.tryToSignUp("", validPassword, validPassword)

        val currentUiState = signUpViewModel.uiStateFlow.value

        val expected = EmptyFieldsException(listOf(AuthField.Email))
        val actual = (currentUiState as SignUpUIState.Error).exception
        assertEquals(expected, actual)
    }

    @Test
    fun `empty password`() {
        signUpViewModel.tryToSignUp(validEmail, "", validPassword)

        val currentUiState = signUpViewModel.uiStateFlow.value

        val expected = EmptyFieldsException(listOf(AuthField.Password))
        val actual = (currentUiState as SignUpUIState.Error).exception
        assertEquals(expected, actual)
    }

    @Test
    fun `invalid email`() {
        signUpViewModel.tryToSignUp(invalidEmail, validPassword, validPassword)

        val currentUiState = signUpViewModel.uiStateFlow.value

        val expected = InvalidFieldsException(listOf(AuthField.Email))
        val actual = (currentUiState as SignUpUIState.Error).exception
        assertEquals(expected, actual)
    }

    @Test
    fun `invalid password`() {
        signUpViewModel.tryToSignUp(validEmail, invalidPassword, invalidPassword)

        val currentUiState = signUpViewModel.uiStateFlow.value

        val expected = InvalidFieldsException(listOf(AuthField.Password))
        val actual = (currentUiState as SignUpUIState.Error).exception
        assertEquals(expected, actual)
    }

    @Test
    fun `passwords mismatch`() {
        val otherValidPassword = "Kotlin1234@#"

        signUpViewModel.tryToSignUp(validEmail, validPassword, otherValidPassword)

        val currentUiState = signUpViewModel.uiStateFlow.value
        val actualException = (currentUiState as SignUpUIState.Error).exception
        assertTrue(actualException is PasswordsMismatchException)
    }
}