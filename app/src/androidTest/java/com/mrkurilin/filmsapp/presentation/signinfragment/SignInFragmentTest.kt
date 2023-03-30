package com.mrkurilin.filmsapp.presentation.signinfragment

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
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
class SignInFragmentTest {

    private val validEmail = "Test@test.test"
    private val validPassword = "Qq12345678"

    private lateinit var signInButton: ViewInteraction
    private lateinit var emailEditText: ViewInteraction
    private lateinit var passwordEditText: ViewInteraction
    private lateinit var signUpTextView: ViewInteraction

    private lateinit var scenario: FragmentScenario<SignInFragment>

    @Before
    fun setUp() {
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_FilmsApp) {
            SignInFragment()
        }
        signInButton = onView(withId(R.id.sign_in_button))
        emailEditText = onView(withId(R.id.email_edit_text))
        passwordEditText = onView(withId(R.id.password_edit_text))
        signUpTextView = onView(withId(R.id.sign_up_text_view))
    }

    @Test
    fun pressSignInWithEmptyFields() {
        signInButton.perform(click())

        checkSignUpGroupIsDisplayed()

        emailEditText.check(matches(hasErrorText(emptyFieldError)))
        passwordEditText.check(matches(hasErrorText(emptyFieldError)))
    }

    @Test
    fun pressSignInWithEmptyPassword() {
        emailEditText.perform(typeText(validEmail))

        signInButton.perform(click())

        checkSignUpGroupIsDisplayed()

        passwordEditText.check(matches(hasErrorText(emptyFieldError)))
    }

    @Test
    fun pressSignInWithEmptyEmail() {
        passwordEditText.perform(typeText(validPassword))

        signInButton.perform(click())

        checkSignUpGroupIsDisplayed()

        emailEditText.check(matches(hasErrorText(emptyFieldError)))
    }

    @Test
    fun pressSignInWithValidEmailAndInvalidPassword() {
        val invalidPassword = "123"

        passwordEditText.perform(typeText(invalidPassword))
        emailEditText.perform(typeText(validEmail))

        signInButton.perform(click())

        checkSignUpGroupIsDisplayed()

        passwordEditText.check(matches(hasErrorText(invalidPasswordError)))
    }

    @Test
    fun pressSignInWithInvalidEmailAndValidPassword() {
        val invalidEmail = "invalidEmail"

        emailEditText.perform(typeText(invalidEmail))
        passwordEditText.perform(typeText(validPassword))

        signInButton.perform(click())

        checkSignUpGroupIsDisplayed()

        emailEditText.check(matches(hasErrorText(invalidEmailError)))
    }

    @Test
    fun testNavigationToSignInFragment() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        scenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        signUpTextView.perform(click())
        assertEquals(navController.currentDestination?.id, R.id.signUpFragment)
    }

    private fun checkSignUpGroupIsDisplayed() {
        emailEditText.check(matches(isDisplayed()))
        passwordEditText.check(matches(isDisplayed()))
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