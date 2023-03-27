package com.mrkurilin.filmsapp.presentation.signinfragment

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mrkurilin.filmsapp.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignInFragmentTest {

    private val validEmail = "Test@test.test"
    private val validPassword = "Qq12345678"
    private val invalidEmail = "invalidEmail"
    private val invalidPassword = "123"

    private lateinit var signInButton: ViewInteraction
    private lateinit var emailEditText: ViewInteraction
    private lateinit var passwordEditText: ViewInteraction

    @Before
    fun setUp() {
        launchFragmentInContainer(themeResId = R.style.Theme_FilmsApp) {
            SignInFragment()
        }
        signInButton = onView(withId(R.id.sign_in_button))
        emailEditText = onView(withId(R.id.email_edit_text))
        passwordEditText = onView(withId(R.id.password_edit_text))
    }

    @Test
    fun pressSignInWithEmptyFields() {
        signInButton.perform(click())
        emailEditText.check(matches(hasErrorText(emptyFieldError)))
        passwordEditText.check(matches(hasErrorText(emptyFieldError)))
    }

    @Test
    fun pressSignInWithEmptyPassword() {
        emailEditText.perform(typeText(validEmail))
        signInButton.perform(click())
        passwordEditText.check(matches(hasErrorText(emptyFieldError)))
    }

    @Test
    fun pressSignInWithEmptyEmail() {
        passwordEditText.perform(typeText(validPassword))
        signInButton.perform(click())
        emailEditText.check(matches(hasErrorText(emptyFieldError)))
    }

    @Test
    fun pressSignInWithValidEmailAndInvalidPassword() {
        passwordEditText.perform(typeText(invalidPassword))
        emailEditText.perform(typeText(validEmail))
        signInButton.perform(click())
        passwordEditText.check(matches(hasErrorText(invalidPasswordError)))
    }

    @Test
    fun pressSignInWithInvalidEmailAndValidPassword() {
        emailEditText.perform(typeText(invalidEmail))
        passwordEditText.perform(typeText(validPassword))
        signInButton.perform(click())
        emailEditText.check(matches(hasErrorText(invalidEmailError)))
    }

    companion object {

        private lateinit var emptyFieldError: String
        private lateinit var invalidPasswordError: String
        private lateinit var invalidEmailError: String

        @BeforeClass
        @JvmStatic
        fun setUpClass() {
            val appContext = InstrumentationRegistry.getInstrumentation().targetContext
            emptyFieldError = appContext.getString(R.string.field_should_not_be_empty)
            invalidPasswordError = appContext.getString(R.string.invalid_password)
            invalidEmailError = appContext.getString(R.string.invalid_email)
        }
    }
}