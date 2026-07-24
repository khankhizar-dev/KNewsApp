package com.android.knewsapp.security

import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import java.util.Date

object JwtUtils {
    
    fun decodeToken(token: String): DecodedJWT? {
        return try {
            JWT.decode(token)
        } catch (e: Exception) {
            null
        }
    }

    fun isTokenExpired(token: String): Boolean {
        val decoded = decodeToken(token) ?: return true
        return decoded.expiresAt.before(Date())
    }

    fun getClaim(token: String, claimName: String): String? {
        val decoded = decodeToken(token) ?: return null
        return decoded.getClaim(claimName).asString()
    }
}
