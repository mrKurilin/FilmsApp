package com.mrkurilin.filmsapp.presentation.signinfragment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrkurilin.filmsapp.domain.credentialvalidation.SignInUser
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignInViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle? = null,
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

    @AssistedFactory
    interface Factory {

        fun create(savedStateHandle: SavedStateHandle): SignInViewModel
    }
}