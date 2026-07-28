package com.android.knewsapp.security

interface SecurityManager {
    fun signData(data: String): String

    fun getPublicKey(): String
}
