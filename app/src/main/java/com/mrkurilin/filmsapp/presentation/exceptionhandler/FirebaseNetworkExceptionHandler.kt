package com.mrkurilin.filmsapp.presentation.exceptionhandler

import android.content.Context
import android.widget.Toast
import com.google.firebase.FirebaseNetworkException
import com.mrkurilin.filmsapp.R

class FirebaseNetworkExceptionHandler(
    private val context: Context,
    private val nextExceptionHandler: ExceptionHandler,
) : ExceptionHandler {

    override fun handle(exception: Throwable) {
        if (exception is FirebaseNetworkException) {
            val text = context.getString(R.string.no_network)
            Toast.makeText(context, text, Toast.LENGTH_LONG).show()
        } else {
            nextExceptionHandler.handle(exception)
        }
    }
}