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

    companion object {
        fun fixture(
            username: String = "user123",
            password: String = "qwer1234",
        ): UserCreate {
            return UserCreate(
                username = username,
                password = password,
            )
        }
    }

}