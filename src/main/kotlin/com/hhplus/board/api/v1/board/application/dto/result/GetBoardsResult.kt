package com.hhplus.board.api.v1.board.application.dto.result

import com.hhplus.board.api.v1.board.domain.model.Board
import java.time.LocalDateTime

data class GetBoardsResult(
    val id: Long,
    val title: String,
    val username: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object{
        fun from(board: Board): GetBoardsResult {
            return GetBoardsResult(
                id = board.id,
                title = board.title,
                username = board.username,
                createdAt = board.createdAt,
                updatedAt = board.updatedAt,
            )
        }
        fun fixture(
            id: Long = 1L,
            title: String = "title",
            username: String = "username",
            createdAt: LocalDateTime = LocalDateTime.now(),
            updatedAt: LocalDateTime = LocalDateTime.now(),
        ): GetBoardsResult {
            return GetBoardsResult(
                id = id,
                title = title,
                username = username,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
        }
    }
}
