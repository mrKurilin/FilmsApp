package com.mrkurilin.filmsapp.data.extensions

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

class FragmentExtensions {

    companion object {

        fun Fragment.showLongToast(
            @StringRes
            res: Int,
        ) {
            Toast.makeText(requireContext(), res, Toast.LENGTH_LONG).show()
        }
    }
}