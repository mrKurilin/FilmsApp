package com.mrkurilin.filmsapp.presentation.signupfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrkurilin.filmsapp.domain.usecase.SignUpCredentialValidation
import com.mrkurilin.filmsapp.domain.usecase.SignUpUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val signUpUser: SignUpUser,
    private val signUpCredentialValidation: SignUpCredentialValidation,
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<SignUpUIState>(
        SignUpUIState.Initial
    )
    val uiStateFlow = _uiStateFlow.asStateFlow()

    fun tryToSignUp(
        email: String,
        password: String,
        confirmPassword: String,
    ) {
        _uiStateFlow.value = SignUpUIState.Loading

        val validationErrors = signUpCredentialValidation.getValidationErrors(
            email = email,
            password = password,
            confirmPassword = confirmPassword,
        )

        if (validationErrors.isNotEmpty()) {
            _uiStateFlow.value = SignUpUIState.ValidationError(
                signUpAuthFieldsWithErrorMessage = validationErrors,
            )
            return
        }

        viewModelScope.launch {
            val result = signUpUser.createUser(
                email = email,
                password = password,
            )

            val state = if (result.isSuccess) {
                SignUpUIState.SignedUp
            } else {
                SignUpUIState.Error(result.requireException())
            }
            _uiStateFlow.value = state
        }
    }
}