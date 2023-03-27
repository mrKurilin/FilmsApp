package com.mrkurilin.filmsapp.data.extensions

import android.content.Context
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

class FragmentExtensions {

    companion object {

        fun Fragment.showLongToast(@StringRes res: Int) {
            Toast.makeText(requireContext(), res, Toast.LENGTH_LONG).show()
        }

        fun Fragment.hideKeyboard() {
            val window: Window = requireActivity().window

            val systemService: Any = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE)
            val inputMethodManager: InputMethodManager = systemService as InputMethodManager

            inputMethodManager.hideSoftInputFromWindow(window.currentFocus?.windowToken, 0)
        }
    }
}