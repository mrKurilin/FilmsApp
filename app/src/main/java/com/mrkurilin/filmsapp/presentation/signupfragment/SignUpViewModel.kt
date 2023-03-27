package com.mrkurilin.filmsapp.presentation.signupfragment

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mrkurilin.filmsapp.data.EmailValidation
import com.mrkurilin.filmsapp.data.PasswordValidation
import com.mrkurilin.filmsapp.data.exceptions.EmptyFieldsException
import com.mrkurilin.filmsapp.data.exceptions.InvalidEmailException
import com.mrkurilin.filmsapp.data.exceptions.InvalidPasswordException
import com.mrkurilin.filmsapp.data.exceptions.PasswordsMismatchException
import kotlinx.coroutines.flow.MutableStateFlow

class SignUpViewModel(
    private val emailValidation: EmailValidation,
    private val passwordValidation: PasswordValidation,
) : ViewModel() {

    val uiStateFlow = MutableStateFlow<SignUpUIState>(SignUpUIState.Initial)

    fun signUpButtonPressed(
        email: String,
        password: String,
        passwordConfirmation: String,
    ) {
        try {
            if (email.isEmpty() || password.isEmpty() || passwordConfirmation.isEmpty()) {
                throw EmptyFieldsException()
            }
            if (emailValidation.isInvalidEmail(email)) {
                throw InvalidEmailException()
            }
            if (passwordValidation.isInvalidPassword(password)) {
                throw InvalidPasswordException()
            }
            if (password != passwordConfirmation) {
                throw PasswordsMismatchException()
            }

            Firebase.auth.createUserWithEmailAndPassword(email, password)

            uiStateFlow.value = SignUpUIState.SignedUp
        } catch (e: Exception) {
            uiStateFlow.value = SignUpUIState.Error(e)
        }
    }
}