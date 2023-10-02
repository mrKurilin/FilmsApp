package com.mrkurilin.filmsapp.data

data class ResultWrapper<T>(
    val data: T? = null,
    val exception: Throwable? = null,
) {

    val isSuccess = data != null
    val isFailure = exception != null

    fun requireData(): T {
        return data ?: throw IllegalStateException("Expected non-null data")
    }

    fun requireException(): Throwable {
        return exception ?: throw IllegalStateException("Expected non-null exception")
    }

    companion object {

        fun <T> createSuccess(data: T): ResultWrapper<T> {
            return ResultWrapper(data = data)
        }

        fun <T> createFailure(
            exception: Throwable,
        ): ResultWrapper<T> {
            return ResultWrapper(exception = exception)
        }
    }
}