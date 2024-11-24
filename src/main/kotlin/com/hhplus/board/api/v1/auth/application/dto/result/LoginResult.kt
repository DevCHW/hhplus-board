package com.hhplus.board.api.v1.auth.application.dto.result

data class LoginResult(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
    val tokenType: String,
)