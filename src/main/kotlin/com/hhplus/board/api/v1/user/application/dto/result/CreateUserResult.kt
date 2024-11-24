package com.hhplus.board.api.v1.user.application.dto.result

import com.hhplus.board.api.v1.user.domain.model.User
import java.time.LocalDateTime

data class CreateUserResult(
    val id: Long,
    val username: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(user: User): CreateUserResult {
            return CreateUserResult(
                id = user.id,
                username = user.username,
                createdAt = user.createdAt,
                updatedAt = user.updatedAt,
            )
        }

        fun fixture(
            id: Long = 1L,
            username: String = "username",
            createdAt: LocalDateTime = LocalDateTime.now(),
            updatedAt: LocalDateTime = LocalDateTime.now(),
        ): CreateUserResult {
            return CreateUserResult(
                id = id,
                username = username,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
        }
    }
}
