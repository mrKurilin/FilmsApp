package com.mrkurilin.filmsapp.di

import android.content.Context
import com.mrkurilin.filmsapp.presentation.signinfragment.SignInViewModel
import com.mrkurilin.filmsapp.presentation.signupfragment.SignUpViewModel
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        AppModule::class,
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance
            context: Context,
        ): AppComponent
    }

    fun signInViewModel(): SignInViewModel

    fun signUpViewModel(): SignUpViewModel
}
