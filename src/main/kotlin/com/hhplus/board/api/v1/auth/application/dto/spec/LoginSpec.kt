package com.hhplus.board.api.v1.auth.application.dto.spec

data class LoginSpec(
    val username: String,
    val password: String,
) {
    companion object {
        fun fixture(
            username: String = "user123",
            password: String = "qwer1234",
        ): LoginSpec {
            return LoginSpec(
                username = username,
                password = password,
            )
        }
    }
}