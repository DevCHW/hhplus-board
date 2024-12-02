package com.hhplus.board.api.v1.auth.controller.dto.request

import com.hhplus.board.api.v1.auth.application.dto.spec.LoginSpec
import jakarta.validation.constraints.NotBlank

data class LoginRequest(

    @field:NotBlank(message = "유저 이름은 필수입니다.")
    val username: String,

    @field:NotBlank(message = "패스워드는 필수입니다.")
    val password: String,
) {
    fun toSpec(): LoginSpec {
        return LoginSpec(
            username = username,
            password = password,
        )
    }

    companion object {
        fun fixture(
            username: String = "username",
            password: String = "qwer1234",
        ): LoginRequest {
            return LoginRequest(
                username = username,
                password = password,
            )
        }
    }
}
