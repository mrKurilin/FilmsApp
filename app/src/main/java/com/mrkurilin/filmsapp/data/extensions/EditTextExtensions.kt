package com.mrkurilin.filmsapp.data.extensions

import android.widget.EditText
import com.mrkurilin.filmsapp.R

class EditTextExtensions {

    companion object {

        fun EditText.setEmptyError() {
            if (text.isBlank()) {
                error = context.getString(R.string.field_should_not_be_empty)
            }
        }

        fun EditText.setInvalidEmailError() {
            error = context.getString(R.string.invalid_email)
        }

        fun EditText.setInvalidPasswordError() {
            error = context.getString(R.string.invalid_password)
        }
    }
}