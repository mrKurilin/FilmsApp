package com.mrkurilin.filmsapp.presentation.signinfragment

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mrkurilin.filmsapp.data.EmailValidation
import com.mrkurilin.filmsapp.data.PasswordValidation
import com.mrkurilin.filmsapp.data.exceptions.EmptyFieldsException
import com.mrkurilin.filmsapp.data.exceptions.InvalidEmailException
import com.mrkurilin.filmsapp.data.exceptions.InvalidPasswordException
import com.mrkurilin.filmsapp.data.exceptions.UnsuccessfulTaskException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignInViewModel(
    private val emailValidation: EmailValidation,
    private val passwordValidation: PasswordValidation,
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<SignInUIState>(SignInUIState.Initial)
    val uiStateFlow: StateFlow<SignInUIState> = _uiStateFlow.asStateFlow()

    fun signInButtonPressed(email: String, password: String) {
        _uiStateFlow.value = SignInUIState.Loading

        if (email.isEmpty() || password.isEmpty()) {
            _uiStateFlow.value = SignInUIState.Error(EmptyFieldsException())
            return
        }

        if (emailValidation.isInvalidEmail(email)) {
            _uiStateFlow.value = SignInUIState.Error(InvalidEmailException())
            return
        }

        if (passwordValidation.isInvalidPassword(password)) {
            _uiStateFlow.value = SignInUIState.Error(InvalidPasswordException())
            return
        }

        Firebase.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _uiStateFlow.value = SignInUIState.SignedIn
            } else {
                val exception = task.exception ?: UnsuccessfulTaskException()
                _uiStateFlow.value = SignInUIState.Error(exception)
            }
        }
    }
}