package com.hhplus.board.api.global.jwt

import com.example.ktboard.domain.error.CoreException
import com.example.ktboard.domain.error.ErrorType
import com.hhplus.board.api.v1.user.domain.model.User
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.core.env.Environment
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.crypto.SecretKey

@Component
class JwtProvider(
    private val env: Environment,
) {
    val secretKey: SecretKey by lazy {
        val secret = env.getProperty("jwt.secret")
            ?: throw IllegalArgumentException("JWT secret is not configured")
        Keys.hmacShaKeyFor(secret.toByteArray())
    }

    val expiration: Long by lazy {
        env.getProperty("jwt.expiration")?.toLong()
            ?: throw IllegalArgumentException("JWT expiration is not configured")
    }

    // create jwt
    fun generateToken(user: User): TokenInfo {
        val issuedAt = System.currentTimeMillis()
        val accessToken = generateAccessToken(user, issuedAt)
        return TokenInfo(
            tokenType = TokenType.BEARER,
            accessToken = accessToken,
            refreshToken = "refreshToken",
            expiresIn = issuedAt + expiration
        )
    }

    // create access token
    private fun generateAccessToken(user: User, issuedAt: Long): String {
        val claims: MutableMap<String, Any> = ConcurrentHashMap()
        claims["id"] = user.id
        return Jwts.builder()
            .setSubject(user.id.toString())
            .setClaims(claims)
            .setIssuedAt(Date(issuedAt))
            .setExpiration(Date(issuedAt + expiration))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    // validate token
    fun isValid(accessToken: String?): Boolean {
        if (!StringUtils.hasText(accessToken)) {
            return false
        }
        parseClaims(accessToken)
        return true
    }

    // parse claims
    fun parseClaims(accessToken: String?): Claims {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
        } catch (e: ExpiredJwtException) {
            throw CoreException(ErrorType.TOKEN_EXPIRED)
        } catch (e: MalformedJwtException) {
            throw CoreException(ErrorType.TOKEN_INVALID)
        } catch (e: SecurityException) {
            throw CoreException(ErrorType.TOKEN_INVALID)
        }
    }

    // get Authentication
    fun getAuthentication(accessToken: String?): Authentication {
        val claims: Claims = parseClaims(accessToken)
        val id: Long = java.lang.Long.valueOf(claims.get("id").toString())

        val userDetails = CustomUserDetails(
            id = id,
        )

        return UsernamePasswordAuthenticationToken(userDetails, accessToken, userDetails.getAuthorities())
    }
}