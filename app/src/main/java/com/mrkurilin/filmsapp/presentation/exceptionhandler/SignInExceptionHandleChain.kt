package com.mrkurilin.filmsapp.presentation.exceptionhandler

import android.content.Context
import android.widget.EditText

class SignInExceptionHandleChain {

    fun handle(
        exception: Throwable,
        emailEditText: EditText,
        passwordEditText: EditText,
        context: Context,
    ) {
        val unknownExceptionHandler = UnknownExceptionHandler(context)

        val emptyFieldsExceptionHandler = EmptyFieldsExceptionHandler(
            emailEditText = emailEditText,
            passwordEditText = passwordEditText,
            nextExceptionHandler = unknownExceptionHandler
        )

        val firebaseAuthExceptionHandler = FirebaseAuthExceptionHandler(
            emailEditText = emailEditText,
            nextExceptionHandler = emptyFieldsExceptionHandler
        )

        val firebaseNetworkException = FirebaseNetworkExceptionHandler(
            context = context,
            nextExceptionHandler = firebaseAuthExceptionHandler
        )

        val invalidFieldsExceptionHandler = InvalidFieldsExceptionHandler(
            emailEditText = emailEditText,
            passwordEditText = passwordEditText,
            nextExceptionHandler = firebaseNetworkException
        )

        invalidFieldsExceptionHandler.handle(exception)
    }
}