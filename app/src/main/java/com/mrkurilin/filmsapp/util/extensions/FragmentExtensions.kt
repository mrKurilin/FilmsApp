package com.mrkurilin.filmsapp.util.extensions

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

fun Fragment.hideKeyboard() {
    val window = requireActivity().window

    val inputMethodManager = requireContext().getSystemService(
        Context.INPUT_METHOD_SERVICE
    ) as InputMethodManager

    inputMethodManager.hideSoftInputFromWindow(window.currentFocus?.windowToken, 0)
}