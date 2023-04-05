package com.mrkurilin.filmsapp.domain.credentialvalidation

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mrkurilin.filmsapp.data.ResultWrapper
import com.mrkurilin.filmsapp.domain.exceptions.EmptyFieldsException
import com.mrkurilin.filmsapp.domain.exceptions.InvalidFieldsException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SignInUser @Inject constructor(
    private val emailValidation: EmailValidation,
    private val passwordValidation: PasswordValidation,
) {

    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String,
    ): ResultWrapper<FirebaseUser> {
        val authTaskResult = try {
            checkForEmptyFields(email, password)
            checkForInvalidFields(email, password)
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
        } catch (exception: Exception) {
            return ResultWrapper.createFailure(exception)
        }

        val user = authTaskResult.user

        return if (user != null) {
            ResultWrapper.createSuccess(user)
        } else {
            ResultWrapper.createFailure(IllegalStateException())
        }
    }

    private fun checkForEmptyFields(email: String, password: String) {
        val emptyFields = mutableListOf<AuthField>()

        if (email.isBlank()) {
            emptyFields.add(AuthField.Email)
        }

        if (password.isBlank()) {
            emptyFields.add(AuthField.Password)
        }

        if (emptyFields.isNotEmpty()) {
            throw EmptyFieldsException(emptyFields)
        }
    }

    private fun checkForInvalidFields(email: String, password: String) {
        val invalidFields = mutableListOf<AuthField>()

        if (emailValidation.isInvalidEmail(email)) {
            invalidFields.add(AuthField.Email)
        }

        if (passwordValidation.isInvalidPassword(password)) {
            invalidFields.add(AuthField.Password)
        }

        if (invalidFields.isNotEmpty()) {
            throw InvalidFieldsException(invalidFields)
        }
    }
}