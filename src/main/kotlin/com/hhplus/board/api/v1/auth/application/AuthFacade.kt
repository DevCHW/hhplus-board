package com.hhplus.board.api.v1.auth.application

import com.example.ktboard.domain.error.CoreException
import com.example.ktboard.domain.error.ErrorType
import com.hhplus.board.api.global.jwt.JwtProvider
import com.hhplus.board.api.v1.auth.application.dto.result.LoginResult
import com.hhplus.board.api.v1.auth.application.dto.spec.LoginSpec
import com.hhplus.board.api.v1.user.domain.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class AuthFacade(
    private val userService: UserService,
    private val jwtProvider: JwtProvider,
    private val passwordEncoder: PasswordEncoder,
) {

    /**
     * 로그인
     */
    fun login(loginSpec: LoginSpec): LoginResult {
        val user = userService.getUserByUsername(loginSpec.username)

        if (!passwordEncoder.matches(loginSpec.password, user.password)) {
            throw CoreException(ErrorType.VALIDATION_ERROR, "비밀번호가 일치하지 않습니다.")
        }

        val token = jwtProvider.generateToken(user)
        return LoginResult(
            accessToken = token.accessToken,
            refreshToken = token.refreshToken,
            expiresIn = token.expiresIn,
            tokenType = token.tokenType.name
        )
    }
}