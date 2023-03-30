package com.mrkurilin.filmsapp.presentation.exceptionhandler

import android.widget.EditText
import com.mrkurilin.filmsapp.R
import com.mrkurilin.filmsapp.domain.credentialvalidation.AuthField
import com.mrkurilin.filmsapp.domain.exceptions.EmptyFieldsException

class EmptyFieldsExceptionHandler(
    private val emailEditText: EditText,
    private val passwordEditText: EditText,
    private val confirmPasswordEditText: EditText? = null,
    private val nextExceptionHandler: ExceptionHandler,
) : ExceptionHandler {

    override fun handle(exception: Throwable) {
        if (exception is EmptyFieldsException) {
            exception.emptyAuthFields.forEach { authField ->
                setEmptyError(authField)
            }
        } else {
            nextExceptionHandler.handle(exception)
        }
    }

    private fun setEmptyError(authField: AuthField) {
        val errorText = emailEditText.context.getString(R.string.field_should_not_be_empty)
        when (authField) {
            AuthField.Email -> {
                emailEditText.error = errorText
            }
            AuthField.Password -> {
                passwordEditText.error = errorText
            }
            AuthField.ConfirmPassword -> {
                confirmPasswordEditText?.error = errorText
            }
        }
    }
}