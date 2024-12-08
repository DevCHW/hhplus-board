package com.hhplus.board.api.v1.board.domain.model

import com.hhplus.board.storage.core.board.BoardEntity

data class CreateBoard(
    val userId: Long,
    val title: String,
    val content: String,
    val username: String,
    val password: String,
) {

    fun toEntity(): BoardEntity {
        return BoardEntity(
            userId = userId,
            username = username,
            title = title,
            content = content,
            password = password,
        )
    }

    companion object {
        fun fixture(
            userId: Long = 1L,
            title: String = "title",
            content: String = "content",
            username: String = "username",
            password: String = "password",
        ): CreateBoard {
            return CreateBoard(
                userId = userId,
                title = title,
                content = content,
                username = username,
                password = password,
            )
        }
    }
}
