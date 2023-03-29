package com.mrkurilin.filmsapp.domain.credentialvalidation

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mrkurilin.filmsapp.data.ResultWrapper
import com.mrkurilin.filmsapp.domain.exceptions.EmptyFieldsException
import com.mrkurilin.filmsapp.domain.exceptions.InvalidFieldsException
import com.mrkurilin.filmsapp.domain.exceptions.PasswordsMismatchException
import kotlinx.coroutines.tasks.await

class SignUpUser(
    private val emailValidation: EmailValidation,
    private val passwordValidation: PasswordValidation,
) {

    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        passwordConfirmation: String,
    ): ResultWrapper<FirebaseUser> {
        val authTaskResult = try {
            checkForEmptyFields(email, password, passwordConfirmation)
            checkForInvalidFields(email, password, passwordConfirmation)
            Firebase.auth.createUserWithEmailAndPassword(email, password).await()
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

    private fun checkForEmptyFields(email: String, password: String, passwordConfirmation: String) {
        val emptyFields = mutableListOf<AuthField>()

        if (email.isBlank()) {
            emptyFields.add(AuthField.Email)
        }
        if (password.isBlank()) {
            emptyFields.add(AuthField.Password)
        }
        if (passwordConfirmation.isBlank()) {
            emptyFields.add(AuthField.ConfirmPassword)
        }

        if (emptyFields.isNotEmpty()) {
            throw EmptyFieldsException(emptyFields)
        }
    }

    private fun checkForInvalidFields(
        email: String,
        password: String,
        passwordConfirmation: String,
    ) {
        if (password != passwordConfirmation) {
            throw PasswordsMismatchException()
        }

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