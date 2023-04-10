package com.mrkurilin.filmsapp.domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mrkurilin.filmsapp.data.ResultWrapper
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SignInUser @Inject constructor() {

    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String,
    ): ResultWrapper<FirebaseUser> {
        return try {
            val user = Firebase.auth.signInWithEmailAndPassword(email, password).await().user
            user ?: throw IllegalStateException()
            ResultWrapper.createSuccess(user)
        } catch (exception: Exception) {
            ResultWrapper.createFailure(exception)
        }
    }
}