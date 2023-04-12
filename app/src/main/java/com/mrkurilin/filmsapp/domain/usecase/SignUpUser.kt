package com.mrkurilin.filmsapp.domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mrkurilin.filmsapp.data.ResultWrapper
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SignUpUser @Inject constructor() {

    suspend fun createUser(
        email: String,
        password: String,
    ): ResultWrapper<FirebaseUser> {
        return try {
            val user = Firebase.auth.createUserWithEmailAndPassword(email, password).await().user
            user ?: throw IllegalStateException("user is null")
            ResultWrapper.createSuccess(user)
        } catch (exception: Exception) {
            ResultWrapper.createFailure(exception)
        }
    }
}