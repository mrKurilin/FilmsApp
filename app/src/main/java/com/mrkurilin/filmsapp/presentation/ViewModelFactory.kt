package com.mrkurilin.filmsapp.presentation

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mrkurilin.filmsapp.domain.credentialvalidation.EmailValidation
import com.mrkurilin.filmsapp.domain.credentialvalidation.PasswordValidation
import com.mrkurilin.filmsapp.domain.credentialvalidation.SignInUser
import com.mrkurilin.filmsapp.domain.credentialvalidation.SignUpUser
import com.mrkurilin.filmsapp.presentation.signinfragment.SignInViewModel
import com.mrkurilin.filmsapp.presentation.signupfragment.SignUpViewModel

class ViewModelFactory {

    companion object {

        private val emailValidation = EmailValidation()
        private val passwordValidation = PasswordValidation()

        val signInViewModel = viewModelFactory {
            initializer {
                SignInViewModel(SignInUser(emailValidation, passwordValidation))
            }
        }

        val signUpViewModel = viewModelFactory {
            initializer {
                SignUpViewModel(SignUpUser(emailValidation, passwordValidation))
            }
        }
    }
}