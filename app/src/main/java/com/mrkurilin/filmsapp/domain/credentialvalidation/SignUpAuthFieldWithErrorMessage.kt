package com.mrkurilin.filmsapp.domain.credentialvalidation

import androidx.annotation.StringRes

sealed class SignUpAuthFieldWithErrorMessage {

    abstract val messageRes: Int

    data class Email(
        @StringRes
        override val messageRes: Int
    ) : SignUpAuthFieldWithErrorMessage()

    data class Password(
        @StringRes
        override val messageRes: Int
    ) : SignUpAuthFieldWithErrorMessage()

    data class ConfirmPassword(
        @StringRes
        override val messageRes: Int
    ) : SignUpAuthFieldWithErrorMessage()
}