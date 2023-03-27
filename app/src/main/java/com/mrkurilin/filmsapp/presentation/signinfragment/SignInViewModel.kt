package com.mrkurilin.filmsapp.presentation.signinfragment

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mrkurilin.filmsapp.data.SignInFieldsValidation
import com.mrkurilin.filmsapp.data.exceptions.UnsuccessfulTaskException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignInViewModel(
    private val signInFieldsValidation: SignInFieldsValidation,
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<SignInUIState>(SignInUIState.Initial)
    val uiStateFlow: StateFlow<SignInUIState> = _uiStateFlow.asStateFlow()

    fun tryToSignIn(email: String, password: String) {
        _uiStateFlow.value = SignInUIState.Loading

        try {
            signInFieldsValidation.validateSignInFields(email, password)

            val auth = Firebase.auth
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _uiStateFlow.value = SignInUIState.SignedIn
                } else {
                    throw task.exception ?: UnsuccessfulTaskException()
                }
            }

        } catch (e: Exception) {
            _uiStateFlow.value = SignInUIState.Error(e)
        }
    }
}