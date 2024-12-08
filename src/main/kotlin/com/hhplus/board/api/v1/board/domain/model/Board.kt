package com.hhplus.board.api.v1.board.domain.model

import com.hhplus.board.storage.core.board.BoardEntity
import java.time.LocalDateTime

data class Board(
    val id: Long,
    val userId: Long,
    val username: String,
    val title: String,
    val content: String,
    val password: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(boardEntity: BoardEntity): Board {
            return Board(
                id = boardEntity.id,
                userId = boardEntity.userId,
                username = boardEntity.username,
                title = boardEntity.title,
                content = boardEntity.content,
                password = boardEntity.password,
                createdAt = boardEntity.createdAt,
                updatedAt = boardEntity.updatedAt,
            )
        }

        fun fixture(
            id: Long = 1L,
            userId: Long = 1L,
            username: String = "username",
            title: String = "title",
            content: String = "content",
            password: String = "1234",
            createdAt: LocalDateTime = LocalDateTime.now(),
            updatedAt: LocalDateTime = LocalDateTime.now(),
        ): Board {
            return Board(
                id = id,
                userId = userId,
                username = username,
                title = title,
                content = content,
                password = password,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
        }
    }
}