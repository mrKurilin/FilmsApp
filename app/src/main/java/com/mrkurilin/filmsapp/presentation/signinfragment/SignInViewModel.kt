package com.mrkurilin.filmsapp.presentation.signinfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrkurilin.filmsapp.domain.credentialvalidation.SignInUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val signInUser: SignInUser,
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<SignInUIState>(SignInUIState.Initial)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    fun tryToSignIn(email: String, password: String) {
        _uiStateFlow.value = SignInUIState.Loading

        viewModelScope.launch {
            val result = signInUser.signInWithEmailAndPassword(email, password)
            if (result.isSuccess) {
                _uiStateFlow.value = SignInUIState.SignedIn
            } else {
                _uiStateFlow.value = SignInUIState.Error(result.requireException())
            }
        }
    }
}