package com.hhplus.board.api.global.jwt

data class TokenInfo(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
    val tokenType: TokenType,
) {
    companion object {
        fun fixture(
            accessToken: String = "access_token",
            refreshToken: String = "refresh_token",
            expiresIn: Long = 12345678L,
            tokenType: TokenType = TokenType.BEARER,
        ): TokenInfo {
            return TokenInfo(
                accessToken = accessToken,
                refreshToken = refreshToken,
                expiresIn = expiresIn,
                tokenType = tokenType,
            )
        }
    }
}
