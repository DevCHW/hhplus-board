package com.hhplus.board.api.v1.auth.controller.dto.response

import com.hhplus.board.api.v1.auth.application.dto.result.LoginResult

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
    val tokenType: String,
    val message: String,
) {
    companion object {
        fun from(loginResult: LoginResult): LoginResponse {
            return LoginResponse(
                accessToken = loginResult.accessToken,
                refreshToken = loginResult.refreshToken,
                expiresIn = loginResult.expiresIn,
                tokenType = loginResult.tokenType,
                message = "로그인에 성공하였습니다!",
            )
        }
    }
}