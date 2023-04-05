package com.mrkurilin.filmsapp.di

import com.mrkurilin.filmsapp.domain.credentialvalidation.EmailValidation
import com.mrkurilin.filmsapp.domain.credentialvalidation.PasswordValidation
import com.mrkurilin.filmsapp.domain.credentialvalidation.SignInUser
import com.mrkurilin.filmsapp.domain.credentialvalidation.SignUpUser
import com.mrkurilin.filmsapp.presentation.exceptionhandler.AuthExceptionHandleChain
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideEmailValidation(): EmailValidation {
        return EmailValidation()
    }

    @Provides
    fun providePasswordValidation(): PasswordValidation {
        return PasswordValidation()
    }

    @Provides
    fun provideSignInUser(
        emailValidation: EmailValidation,
        passwordValidation: PasswordValidation,
    ): SignInUser {
        return SignInUser(emailValidation, passwordValidation)
    }

    @Provides
    fun provideSignUpUser(
        emailValidation: EmailValidation,
        passwordValidation: PasswordValidation,
    ): SignUpUser {
        return SignUpUser(emailValidation, passwordValidation)
    }

    @Provides
    fun provideAuthExceptionHandleChain(): AuthExceptionHandleChain {
        return AuthExceptionHandleChain()
    }
}