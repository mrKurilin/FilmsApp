package com.mrkurilin.filmsapp.data

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mrkurilin.filmsapp.presentation.signinfragment.SignInViewModel

class ViewModelFactory {

    companion object {

        val SignInViewModel: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SignInViewModel(
                    emailValidation = EmailValidation(),
                    passwordValidation = PasswordValidation()
                )
            }
        }
    }
}