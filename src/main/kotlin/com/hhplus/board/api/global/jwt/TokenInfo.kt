package com.hhplus.board.api.global.jwt

data class TokenInfo(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
    val tokenType: TokenType,
)
