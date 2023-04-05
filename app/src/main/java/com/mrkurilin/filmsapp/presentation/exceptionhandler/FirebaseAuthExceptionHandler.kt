package com.mrkurilin.filmsapp.presentation.exceptionhandler

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuthException
import com.mrkurilin.filmsapp.R

class FirebaseAuthExceptionHandler(
    private val context: Context,
    private val nextExceptionHandler: ExceptionHandler,
) : ExceptionHandler {

    override fun handle(exception: Throwable) {
        if (exception is FirebaseAuthException) {
            val text = context.getString(R.string.wrong_email_or_password)
            Toast.makeText(context, text, Toast.LENGTH_LONG).show()
        } else {
            nextExceptionHandler.handle(exception)
        }
    }
}