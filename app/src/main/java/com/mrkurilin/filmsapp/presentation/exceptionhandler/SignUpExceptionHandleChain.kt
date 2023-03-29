package com.mrkurilin.filmsapp.presentation.exceptionhandler

import android.content.Context
import android.widget.EditText

class SignUpExceptionHandleChain {

    fun handle(
        exception: Throwable,
        emailEditText: EditText,
        passwordEditText: EditText,
        confirmPasswordEditText: EditText,
        context: Context,
    ) {
        val unknownExceptionHandler = UnknownExceptionHandler(context)

        val emptyFieldsExceptionHandler = EmptyFieldsExceptionHandler(
            emailEditText = emailEditText,
            passwordEditText = passwordEditText,
            confirmPasswordEditText = confirmPasswordEditText,
            nextExceptionHandler = unknownExceptionHandler
        )

        val firebaseAuthExceptionHandler = FirebaseAuthExceptionHandler(
            emailEditText = emailEditText,
            nextExceptionHandler = emptyFieldsExceptionHandler
        )

        val invalidFieldsExceptionHandler = InvalidFieldsExceptionHandler(
            emailEditText = emailEditText,
            passwordEditText = passwordEditText,
            nextExceptionHandler = firebaseAuthExceptionHandler
        )

        val firebaseNetworkException = FirebaseNetworkExceptionHandler(
            context = context,
            nextExceptionHandler = invalidFieldsExceptionHandler
        )

        val passwordsMismatchExceptionHandler = PasswordsMismatchExceptionHandler(
            confirmPasswordEditText = confirmPasswordEditText,
            firebaseNetworkException
        )

        passwordsMismatchExceptionHandler.handle(exception)
    }
}