package com.mrkurilin.filmsapp.domain.credentialvalidation

import androidx.annotation.StringRes

sealed class SignInAuthFieldWithErrorMessage {

    abstract val messageRes: Int

    data class Email(
        @StringRes
        override val messageRes: Int
    ) : SignInAuthFieldWithErrorMessage()

    data class Password(
        @StringRes
        override val messageRes: Int
    ) : SignInAuthFieldWithErrorMessage()
}