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
    }
}