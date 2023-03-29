package com.mrkurilin.filmsapp.presentation.exceptionhandler

import android.widget.EditText
import com.mrkurilin.filmsapp.R
import com.mrkurilin.filmsapp.domain.exceptions.PasswordsMismatchException

class PasswordsMismatchExceptionHandler(
    private val confirmPasswordEditText: EditText,
    private val nextExceptionHandler: ExceptionHandler,
) : ExceptionHandler {

    override fun handle(exception: Throwable) {
        if (exception is PasswordsMismatchException) {
            confirmPasswordEditText.error = confirmPasswordEditText.context.getString(
                R.string.mismatch_password
            )
        } else {
            nextExceptionHandler.handle(exception)
        }
    }
}