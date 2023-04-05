package com.mrkurilin.filmsapp.util.extensions

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mrkurilin.filmsapp.FilmsApp
import com.mrkurilin.filmsapp.di.AppComponent
import com.mrkurilin.filmsapp.di.Factory

fun Fragment.hideKeyboard() {
    val window = requireActivity().window

    val inputMethodManager = requireContext().getSystemService(
        Context.INPUT_METHOD_SERVICE
    ) as InputMethodManager

    inputMethodManager.hideSoftInputFromWindow(window.currentFocus?.windowToken, 0)
}

fun Fragment.appComponent(): AppComponent = (requireActivity().application as FilmsApp).appComponent

inline fun <reified T : ViewModel> Fragment.lazyViewModel(
    noinline create: (stateHandle: SavedStateHandle) -> T,
) = viewModels<T> {
    Factory(this, create)
}