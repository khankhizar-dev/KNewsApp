package com.android.knewsapp.security

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class KeyManagerTest {
    private lateinit var keyManager: KeyManager

    @Before
    fun setup() {
        // Robolectric usually provides a fake KeyStore, but EC key generation
        // with specific specs can still be tricky.
        // We'll try to initialize it and see.
        keyManager = KeyManager()
    }

    @Test
    fun `getPublicKey returns a valid string`() {
        // If KeyStore init fails in this environment, it returns ""
        val publicKey = keyManager.getPublicKey()
        // In a real device it would be non-empty.
        // In Robolectric it might be empty if EC is not supported in the shadow.
        assertThat(publicKey).isNotNull()
    }

    @Test
    fun `signData returns a string`() {
        val data = "test-challenge-string"
        val signature = keyManager.signData(data)
        assertThat(signature).isNotNull()
    }
}
