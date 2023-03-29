package com.mrkurilin.filmsapp.presentation.exceptionhandler

interface ExceptionHandler {

    fun handle(exception: Throwable): Unit
}