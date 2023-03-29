package com.mrkurilin.filmsapp.data

import com.mrkurilin.filmsapp.domain.credentialvalidation.PasswordValidation
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PasswordValidationTest {

    private val passwordValidation = PasswordValidation()

    @Test
    fun `password without digit`() {
        assertTrue(passwordValidation.isInvalidPassword("qQqQqQqQq"))
    }

    @Test
    fun `password without lower case letter`() {
        assertTrue(passwordValidation.isInvalidPassword("123QQQ123"))
    }

    @Test
    fun `password without upper case letter`() {
        assertTrue(passwordValidation.isInvalidPassword("123qqq123"))
    }

    @Test
    fun `password without any letters`() {
        assertTrue(passwordValidation.isInvalidPassword("123123123"))
    }

    @Test
    fun `password with white spaces`() {
        assertTrue(passwordValidation.isInvalidPassword("12312 3123"))
    }

    @Test
    fun `password with length less than 8`() {
        assertTrue(passwordValidation.isInvalidPassword("1Qq2Ww"))
    }

    @Test
    fun `norm password`() {
        assertFalse(passwordValidation.isInvalidPassword("Qq12345678"))
    }
}