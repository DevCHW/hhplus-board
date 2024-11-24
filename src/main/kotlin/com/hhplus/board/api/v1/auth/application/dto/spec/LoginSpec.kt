package com.hhplus.board.api.v1.auth.application.dto.spec

data class LoginSpec(
    val username: String,
    val password: String,
) {
}