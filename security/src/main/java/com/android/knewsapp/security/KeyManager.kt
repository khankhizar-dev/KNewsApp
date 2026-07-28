package com.android.knewsapp.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.Signature
import java.security.interfaces.ECPublicKey
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KeyManager
    @Inject
    constructor() : SecurityManager {
        companion object {
            private const val KEY_ALIAS = "KNewsApp_EC_Key"
            private const val ANDROID_KEYSTORE = "AndroidKeyStore"
        }

        init {
            generateEcKeyIfNeeded()
        }

        private fun generateEcKeyIfNeeded() {
            try {
                val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
                if (!keyStore.containsAlias(KEY_ALIAS)) {
                    val keyPairGenerator =
                        KeyPairGenerator.getInstance(
                            KeyProperties.KEY_ALGORITHM_EC,
                            ANDROID_KEYSTORE,
                        )

                    val spec =
                        KeyGenParameterSpec.Builder(
                            KEY_ALIAS,
                            KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY,
                        )
                            .setDigests(KeyProperties.DIGEST_SHA256)
                            .setUserAuthenticationRequired(false)
                            .build()

                    keyPairGenerator.initialize(spec)
                    keyPairGenerator.generateKeyPair()
                }
            } catch (e: Exception) {
                // Log or handle appropriately for production
            }
        }

        override fun signData(data: String): String {
            return try {
                val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
                val privateKey = keyStore.getKey(KEY_ALIAS, null) as java.security.PrivateKey

                val signature = Signature.getInstance("SHA256withECDSA")
                signature.initSign(privateKey)
                signature.update(data.toByteArray())

                Base64.encodeToString(signature.sign(), Base64.NO_WRAP)
            } catch (e: Exception) {
                ""
            }
        }

        override fun getPublicKey(): String {
            return try {
                val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
                val certificate = keyStore.getCertificate(KEY_ALIAS) ?: return ""
                val publicKey = certificate.publicKey as ECPublicKey
                Base64.encodeToString(publicKey.encoded, Base64.NO_WRAP)
            } catch (e: Exception) {
                ""
            }
        }
    }
