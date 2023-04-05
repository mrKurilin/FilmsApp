package com.mrkurilin.filmsapp.presentation.exceptionhandler

import android.widget.EditText
import com.mrkurilin.filmsapp.R
import com.mrkurilin.filmsapp.domain.credentialvalidation.AuthField
import com.mrkurilin.filmsapp.domain.exceptions.InvalidFieldsException

class InvalidFieldsExceptionHandler(
    private val emailEditText: EditText,
    private val passwordEditText: EditText,
    private val nextExceptionHandler: ExceptionHandler,
) : ExceptionHandler {

    override fun handle(exception: Throwable) {
        if (exception is InvalidFieldsException) {
            exception.invalidAuthFields.forEach { authField ->
                setInvalidFieldError(authField)
            }
        } else {
            nextExceptionHandler.handle(exception)
        }
    }

    private fun setInvalidFieldError(authField: AuthField) {
        if (authField == AuthField.Email) {
            emailEditText.error = emailEditText.context.getString(R.string.invalid_email)
        }
        if (authField == AuthField.Password) {
            passwordEditText.error = passwordEditText.context.getString(R.string.invalid_password)
        }
    }
}