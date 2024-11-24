package com.hhplus.board.api.v1.auth.application.dto.result

data class LoginResult(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
    val tokenType: String,
) {
    companion object {
        fun fixture(
            accessToken: String = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiaWF0IjoxNzMyNDU0MTE3LCJleHAiOjE3MzI0NTQyMDR9.NEJDZi746vGvApCbniCdnaBZw0Ur6N6dhNJYv9nE7qY",
            refreshToken: String = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiaWF0IjoxNzMyNDU0MTE3LCJleHAiOjE3MzI0NTQyMDR9.NEJDZi746vGvApCbniCdnaBZw0Ur6N6dhNJYv9nE7qY",
            expiresIn: Long = 12345678,
            tokenType: String = "BEARER",
        ): LoginResult{
            return LoginResult(
                accessToken = accessToken,
                refreshToken = refreshToken,
                expiresIn = expiresIn,
                tokenType = tokenType,
            )
        }
    }
}