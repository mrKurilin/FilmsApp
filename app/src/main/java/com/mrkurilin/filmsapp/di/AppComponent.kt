package com.mrkurilin.filmsapp.di

import android.content.Context
import com.mrkurilin.filmsapp.presentation.signinfragment.SignInFragment
import com.mrkurilin.filmsapp.presentation.signinfragment.SignInViewModel
import com.mrkurilin.filmsapp.presentation.signupfragment.SignUpFragment
import com.mrkurilin.filmsapp.presentation.signupfragment.SignUpViewModel
import dagger.BindsInstance
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): AppComponent
    }

    fun signInViewModel(): SignInViewModel.Factory

    fun signUpViewModel(): SignUpViewModel.Factory

    fun inject(signInFragment: SignInFragment)

    fun inject(signUpFragment: SignUpFragment)
}
