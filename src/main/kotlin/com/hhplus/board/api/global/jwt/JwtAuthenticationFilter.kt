package com.hhplus.board.api.global.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.ObjectUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

@Component
class JwtAuthenticationFilter (
    private val jwtProvider: JwtProvider,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val accessToken = resolveToken(request)

        // accessToken 검증
        if (jwtProvider.isValid(accessToken)) {
            setAuthentication(accessToken)
        }

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val excludeFilterPath = arrayOf("POST /api/auth/login")
        val path = "${request.method} ${request.requestURI}"
        return Arrays.stream(excludeFilterPath)
            .anyMatch { prefix: String? -> path.startsWith(prefix!!) }
    }

    private fun setAuthentication(accessToken: String?) {
        val authentication: Authentication = jwtProvider.getAuthentication(accessToken)
        SecurityContextHolder.getContext().authentication = authentication
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val token = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (ObjectUtils.isEmpty(token) || !token.startsWith(BEARER_TOKEN_PREFIX)) {
            return null
        }
        return token.substring(BEARER_TOKEN_PREFIX.length)
    }

    companion object {
        const val BEARER_TOKEN_PREFIX = "Bearer "
    }
}