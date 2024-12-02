package com.hhplus.board.api.v1.user.controller.dto.request

import com.hhplus.board.api.v1.user.application.dto.spec.SignUpSpec

data class UserCreateRequest(
    val username: String,
    val password: String,
) {
    fun toSpec(): SignUpSpec {
        return SignUpSpec(
            username = username,
            password = password,
        )
    }

    companion object {
        fun fixture(
            username: String = "username",
            password: String = "password",
        ): SignUpSpec {
            return SignUpSpec(
                username = username,
                password = password,
            )
        }
    }
}
