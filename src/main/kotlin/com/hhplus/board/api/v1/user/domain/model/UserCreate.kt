package com.hhplus.board.api.v1.user.domain.model

import com.hhplus.board.storage.core.entity.UserEntity

data class UserCreate(
    val username: String,
    val password: String,
) {

    fun toEntity(): UserEntity {
        return UserEntity(
            username = username,
            password = password,
        )
    }

}