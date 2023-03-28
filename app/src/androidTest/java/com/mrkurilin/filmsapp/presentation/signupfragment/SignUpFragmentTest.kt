package com.mrkurilin.filmsapp.presentation.signupfragment

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mrkurilin.filmsapp.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignUpFragmentTest {

    private val validEmail = "Test@test.test"
    private val invalidEmail = "invalidEmail"
    private val validPassword = "Qq12345678"
    private val otherValidPassword = "Qq123456789"
    private val invalidPassword = "123"

    private lateinit var signUpButton: ViewInteraction
    private lateinit var emailEditText: ViewInteraction
    private lateinit var passwordEditText: ViewInteraction
    private lateinit var repeatedPasswordEditText: ViewInteraction
    private lateinit var signInTextView: ViewInteraction

    private lateinit var scenario: FragmentScenario<SignUpFragment>

    @Before
    fun setUp() {
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_FilmsApp) {
            SignUpFragment()
        }.moveToState(Lifecycle.State.STARTED)

        signUpButton = onView(withId(R.id.sign_up_button))
        emailEditText = onView(withId(R.id.email_edit_text))
        passwordEditText = onView(withId(R.id.password_edit_text))
        repeatedPasswordEditText = onView(withId(R.id.repeat_password_edit_text))
        signInTextView = onView(withId(R.id.sign_in_text_view))
    }

    @Test
    fun pressSignUpWithEmptyFields() {
        signUpButton.perform(click())

        checkSignUpGroupIsDisplayed()

        emailEditText.check(matches(hasErrorText(emptyFieldError)))
        passwordEditText.check(matches(hasErrorText(emptyFieldError)))
        repeatedPasswordEditText.check(matches(hasErrorText(emptyFieldError)))
    }

    @Test
    fun pressSignUpWithEmptyPasswords() {
        emailEditText.perform(typeText(validEmail))

        signUpButton.perform(click())

        checkSignUpGroupIsDisplayed()

        passwordEditText.check(matches(hasErrorText(emptyFieldError)))
        repeatedPasswordEditText.check(matches(hasErrorText(emptyFieldError)))
    }

    @Test
    fun pressSignUpWithEmptyEmail() {
        passwordEditText.perform(typeText(validPassword))
        repeatedPasswordEditText.perform(typeText(validPassword))

        signUpButton.perform(click())

        checkSignUpGroupIsDisplayed()

        emailEditText.check(matches(hasErrorText(emptyFieldError)))
    }

    @Test
    fun pressSignUpWithValidEmailAndInvalidPassword() {
        emailEditText.perform(typeText(validEmail))
        passwordEditText.perform(typeText(invalidPassword))
        repeatedPasswordEditText.perform(typeText(invalidPassword))

        signUpButton.perform(click())

        checkSignUpGroupIsDisplayed()

        passwordEditText.check(matches(hasErrorText(invalidPasswordError)))
    }

    @Test
    fun pressSignUpWithInvalidEmailAndValidPasswords() {
        emailEditText.perform(typeText(invalidEmail))
        passwordEditText.perform(typeText(validPassword))
        repeatedPasswordEditText.perform(typeText(validPassword))

        signUpButton.perform(click())

        checkSignUpGroupIsDisplayed()

        emailEditText.check(matches(hasErrorText(invalidEmailError)))
    }

    @Test
    fun pressSignUpWithValidEmailAndDifferentPasswords() {
        emailEditText.perform(typeText(validEmail))
        passwordEditText.perform(typeText(validPassword))
        repeatedPasswordEditText.perform(typeText(otherValidPassword))

        signUpButton.perform(click())

        checkSignUpGroupIsDisplayed()

        repeatedPasswordEditText.check(matches(hasErrorText(mismatchPassword)))
    }

    @Test
    fun testNavigationToSignInFragment() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        scenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            navController.navigate(R.id.signUpFragment)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        signInTextView.perform(click())
        assertEquals(navController.currentDestination?.id, R.id.signInFragment)
    }

    private fun checkSignUpGroupIsDisplayed() {
        emailEditText.check(matches(isDisplayed()))
        passwordEditText.check(matches(isDisplayed()))
        repeatedPasswordEditText.check(matches(isDisplayed()))
    }

    companion object {

        private lateinit var emptyFieldError: String
        private lateinit var invalidPasswordError: String
        private lateinit var invalidEmailError: String
        private lateinit var mismatchPassword: String

        @BeforeClass
        @JvmStatic
        fun setUpClass() {
            val appContext = InstrumentationRegistry.getInstrumentation().targetContext
            emptyFieldError = appContext.getString(R.string.field_should_not_be_empty)
            invalidPasswordError = appContext.getString(R.string.invalid_password)
            invalidEmailError = appContext.getString(R.string.invalid_email)
            mismatchPassword = appContext.getString(R.string.mismatch_password)
        }
    }
}