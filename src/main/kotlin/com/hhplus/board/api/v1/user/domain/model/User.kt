package com.hhplus.board.api.v1.user.domain.model

import com.hhplus.board.storage.core.entity.UserEntity
import java.time.LocalDateTime

data class User(
    val id: Long,
    val username: String,
    val password: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {

    companion object {
        fun from(userEntity: UserEntity): User {
            return User(
                id = userEntity.id,
                username = userEntity.username,
                password = userEntity.password,
                createdAt = userEntity.createdAt,
                updatedAt = userEntity.updatedAt,
            )
        }
    }

}

