package com.hhplus.board.api.v1.board.application.dto.result

import com.hhplus.board.api.v1.board.domain.model.Board
import java.time.LocalDateTime

data class GetBoardResult(
    val id: Long,
    val title: String,
    val content: String,
    val username: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(board: Board): GetBoardResult {
            return GetBoardResult(
                id = board.id,
                title = board.title,
                content = board.content,
                username = board.username,
                createdAt = board.createdAt,
                updatedAt = board.updatedAt,
            )
        }
    }
}