package com.hhplus.board.api.v1.user.controller.dto.request

import com.hhplus.board.api.v1.user.application.dto.spec.CreateUserSpec

data class UserCreateRequest(
    val username: String,
    val password: String,
) {
    fun toSpec(): CreateUserSpec {
        return CreateUserSpec(
            username = username,
            password = password,
        )
    }
}
