package com.android.knewsapp.security

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class JwtUtilsTest {
    @Test
    fun `decodeToken with invalid token returns null`() {
        val result = JwtUtils.decodeToken("invalid-token")
        assertThat(result).isNull()
    }

    @Test
    fun `isTokenExpired with invalid token returns true`() {
        val result = JwtUtils.isTokenExpired("invalid-token")
        assertThat(result).isTrue()
    }

    @Test
    fun `getClaim with invalid token returns null`() {
        val result = JwtUtils.getClaim("invalid-token", "sub")
        assertThat(result).isNull()
    }
}
