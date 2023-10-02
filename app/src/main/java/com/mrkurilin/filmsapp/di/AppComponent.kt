package com.mrkurilin.filmsapp.di

import android.content.Context
import com.mrkurilin.filmsapp.presentation.film_details.filmdetailsfragment.FilmDetailsViewModel
import com.mrkurilin.filmsapp.presentation.signinfragment.SignInViewModel
import com.mrkurilin.filmsapp.presentation.signupfragment.SignUpViewModel
import com.mrkurilin.filmsapp.presentation.top_films.topfilmsfragment.TopFilmsViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DataModule::class,
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

    fun topFilmsViewModel(): TopFilmsViewModel

    fun filmDetailsViewModel(): FilmDetailsViewModel
}
