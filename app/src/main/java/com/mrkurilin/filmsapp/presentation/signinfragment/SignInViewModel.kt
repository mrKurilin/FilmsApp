package com.mrkurilin.filmsapp.presentation.signinfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrkurilin.filmsapp.domain.exceptions.InvalidSignInCredentialException
import com.mrkurilin.filmsapp.domain.usecase.SignInCredentialValidation
import com.mrkurilin.filmsapp.domain.usecase.SignInUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val signInUser: SignInUser,
    private val signInCredentialValidation: SignInCredentialValidation,
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<SignInUIState>(
        SignInUIState.Initial
    )
    val uiStateFlow = _uiStateFlow.asStateFlow()

    fun tryToSignIn(email: String, password: String) {
        _uiStateFlow.value = SignInUIState.Loading

        try {
            signInCredentialValidation.validate(
                email = email,
                password = password
            )
        } catch (exception: InvalidSignInCredentialException) {
            _uiStateFlow.value = SignInUIState.ValidationError(exception.errors)
        }

        viewModelScope.launch {
            val result = signInUser.signInWithEmailAndPassword(
                email = email,
                password = password
            )
            val state = if (result.isSuccess) {
                SignInUIState.SignedIn
            } else {
                SignInUIState.Error(result.requireException())
            }
            _uiStateFlow.value = state
        }
    }
}