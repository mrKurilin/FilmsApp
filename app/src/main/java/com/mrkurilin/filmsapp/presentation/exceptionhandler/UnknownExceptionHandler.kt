package com.mrkurilin.filmsapp.presentation.exceptionhandler

import android.content.Context
import android.widget.Toast
import com.mrkurilin.filmsapp.R

class UnknownExceptionHandler(private val context: Context) : ExceptionHandler {

    override fun handle(exception: Throwable) {
        exception.printStackTrace()
        Toast.makeText(context, R.string.unknown_error, Toast.LENGTH_LONG).show()
    }
}