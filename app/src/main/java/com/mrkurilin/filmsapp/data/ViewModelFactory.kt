package com.mrkurilin.filmsapp.data

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mrkurilin.filmsapp.presentation.signinfragment.SignInViewModel
import com.mrkurilin.filmsapp.presentation.signupfragment.SignUpViewModel

class ViewModelFactory {

    companion object {

        private val emailValidation = EmailValidation()
        private val passwordValidation = PasswordValidation()

        val SignInViewModel: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SignInViewModel(
                    signInFieldsValidation = SignInFieldsValidation(
                        emailValidation,
                        passwordValidation
                    )
                )
            }
        }

        val SignUpViewModel: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SignUpViewModel(
                    signUpFieldsValidation = SignUpFieldsValidation(
                        emailValidation,
                        passwordValidation
                    )
                )
            }
        }
    }
}