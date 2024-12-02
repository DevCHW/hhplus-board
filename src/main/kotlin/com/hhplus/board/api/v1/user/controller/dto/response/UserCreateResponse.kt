package com.hhplus.board.api.v1.user.controller.dto.response

import com.hhplus.board.api.v1.user.application.dto.result.SignUpResult
import java.time.LocalDateTime

data class UserCreateResponse(
    val id: Long,
    val username: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val resultMessage: String,
) {
    companion object {
        fun from(createUserResult: SignUpResult): UserCreateResponse {
            return UserCreateResponse(
                id = createUserResult.id,
                username = createUserResult.username,
                createdAt = createUserResult.createdAt,
                updatedAt = createUserResult.updatedAt,
                resultMessage = "회원가입에 성공하였습니다!",
            )
        }
    }
}
