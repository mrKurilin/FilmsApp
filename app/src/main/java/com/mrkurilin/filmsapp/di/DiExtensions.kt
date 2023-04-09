package com.mrkurilin.filmsapp.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.mrkurilin.filmsapp.FilmsApp
import com.mrkurilin.filmsapp.presentation.ViewModelFactory

fun Fragment.appComponent(): AppComponent {
    return (requireActivity().application as FilmsApp).appComponent
}

inline fun <reified T : ViewModel> Fragment.lazyViewModel(
    noinline create: () -> T,
): Lazy<T> {
    return viewModels {
        ViewModelFactory(this, create)
    }
}