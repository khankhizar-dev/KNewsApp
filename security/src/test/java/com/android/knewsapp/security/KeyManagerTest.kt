package com.android.knewsapp.security

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33]) // Mocking Android SDK
class KeyManagerTest {

    @Test
    fun `getPublicKey returns a valid non-empty string`() {
        val publicKey = KeyManager.getPublicKey()
        assertThat(publicKey).isNotEmpty()
    }

    @Test
    fun `signData returns a signature for given string`() {
        val data = "test-challenge-string"
        val signature = KeyManager.signData(data)
        assertThat(signature).isNotEmpty()
    }

    @Test
    fun `different data produces different signatures`() {
        val sig1 = KeyManager.signData("data-1")
        val sig2 = KeyManager.signData("data-2")
        assertThat(sig1).isNotEqualTo(sig2)
    }
}
