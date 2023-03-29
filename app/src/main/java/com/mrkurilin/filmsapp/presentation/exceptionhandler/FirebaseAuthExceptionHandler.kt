package com.mrkurilin.filmsapp.presentation.exceptionhandler

import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuthException
import com.mrkurilin.filmsapp.R

class FirebaseAuthExceptionHandler(
    private val emailEditText: EditText,
    private val nextExceptionHandler: ExceptionHandler,
) : ExceptionHandler {

    override fun handle(exception: Throwable) {
        if (exception is FirebaseAuthException) {
            val text = emailEditText.context.getString(R.string.wrong_email_or_password)
            Toast.makeText(emailEditText.context, text, Toast.LENGTH_LONG).show()
        } else {
            nextExceptionHandler.handle(exception)
        }
    }
}