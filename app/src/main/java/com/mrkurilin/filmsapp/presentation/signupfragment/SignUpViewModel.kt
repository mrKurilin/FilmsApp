package com.mrkurilin.filmsapp.presentation.signupfragment

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mrkurilin.filmsapp.data.SignUpFieldsValidation
import com.mrkurilin.filmsapp.data.exceptions.UnsuccessfulTaskException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignUpViewModel(
    private val signUpFieldsValidation: SignUpFieldsValidation,
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<SignUpUIState>(SignUpUIState.Initial)
    val uiStateFlow: StateFlow<SignUpUIState> = _uiStateFlow.asStateFlow()

    fun tryToSignUp(email: String, password: String, passwordConfirmation: String) {
        _uiStateFlow.value = SignUpUIState.Loading

        try {
            signUpFieldsValidation.validateSignUpFields(email, password, passwordConfirmation)

            val auth = Firebase.auth
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _uiStateFlow.value = SignUpUIState.SignedUp
                } else {
                    throw task.exception ?: UnsuccessfulTaskException()
                }
            }
        } catch (e: Exception) {
            _uiStateFlow.value = SignUpUIState.Error(e)
        }
    }
}
