package com.mrkurilin.filmsapp.presentation.signupfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrkurilin.filmsapp.domain.credentialvalidation.SignUpUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val signUpUser: SignUpUser,
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<SignUpUIState>(SignUpUIState.Initial)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    fun tryToSignUp(email: String, password: String, passwordConfirmation: String) {
        _uiStateFlow.value = SignUpUIState.Loading

        viewModelScope.launch {
            val result = signUpUser.createUserWithEmailAndPassword(
                email,
                password,
                passwordConfirmation
            )
            if (result.isSuccess) {
                _uiStateFlow.value = SignUpUIState.SignedUp
            } else {
                _uiStateFlow.value = SignUpUIState.Error(result.requireException())
            }
        }
    }
}
