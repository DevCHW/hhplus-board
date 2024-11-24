package com.hhplus.board.api.v1.user.application.dto.spec

import com.hhplus.board.api.v1.user.domain.model.UserCreate

data class CreateUserSpec(
    val username: String,
    val password: String,
) {
    init {
        // 유저네임 검사

        // 패스워드 검사
    }
    fun toModel(): UserCreate {
        return UserCreate(
            username = username,
            password = password,
        )
    }
}
