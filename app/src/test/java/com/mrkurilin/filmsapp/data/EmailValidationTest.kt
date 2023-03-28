package com.mrkurilin.filmsapp.data

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class EmailValidationTest {

    private val emailValidation = EmailValidation()

    @Test
    fun `valid email`() {
        val validEmail = "john.doe@example.com"
        assertFalse(emailValidation.isInvalidEmail(validEmail))
    }

    @Test
    fun `invalid email with no domain`() {
        val invalidEmail = "john.doe@"
        assertTrue(emailValidation.isInvalidEmail(invalidEmail))
    }

    @Test
    fun `invalid email with no username`() {
        val invalidEmail = "@example.com"
        assertTrue(emailValidation.isInvalidEmail(invalidEmail))
    }

    @Test
    fun `invalid email with special character`() {
        val invalidEmail = "jane.doe&example.com"
        assertTrue(emailValidation.isInvalidEmail(invalidEmail))
    }

    @Test
    fun `invalid email with spaces`() {
        val invalidEmail = "john doe@example.com"
        assertTrue(emailValidation.isInvalidEmail(invalidEmail))
    }

    @Test
    fun `invalid email with multiple at symbols`() {
        val invalidEmail = "john.doe@example@com"
        assertTrue(emailValidation.isInvalidEmail(invalidEmail))
    }
}