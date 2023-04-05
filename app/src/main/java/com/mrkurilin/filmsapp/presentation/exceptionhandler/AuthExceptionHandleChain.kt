package com.mrkurilin.filmsapp.presentation.exceptionhandler

import android.content.Context
import android.widget.EditText

class AuthExceptionHandleChain {

    fun handle(
        exception: Throwable,
        emailEditText: EditText,
        passwordEditText: EditText,
        context: Context,
        confirmPasswordEditText: EditText? = null,
    ) {
        val unknownExceptionHandler = UnknownExceptionHandler(context)

        val emptyFieldsExceptionHandler = EmptyFieldsExceptionHandler(
            emailEditText = emailEditText,
            passwordEditText = passwordEditText,
            confirmPasswordEditText = confirmPasswordEditText,
            nextExceptionHandler = unknownExceptionHandler
        )

        val firebaseAuthExceptionHandler = FirebaseAuthExceptionHandler(
            context = context,
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

        if (confirmPasswordEditText == null) {
            firebaseNetworkException.handle(exception)
        } else {
            PasswordsMismatchExceptionHandler(
                confirmPasswordEditText = confirmPasswordEditText,
                nextExceptionHandler = firebaseNetworkException
            ).handle(exception)
        }
    }
}