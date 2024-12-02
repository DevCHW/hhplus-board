package com.hhplus.board.api.v1.board.controller.dto.response

import com.hhplus.board.api.v1.board.application.dto.result.GetBoardsResult
import java.time.LocalDateTime

data class BoardsResponse(
    val id: Long,
    val title: String,
    val username: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(result: GetBoardsResult): BoardsResponse {
            return BoardsResponse(
                id = result.id,
                title = result.title,
                username = result.username,
                createdAt = result.createdAt,
                updatedAt = result.updatedAt,
            )
        }

        fun fixture(
            id: Long = 1L,
            title: String = "title",
            username: String = "username",
            createdAt: LocalDateTime = LocalDateTime.now(),
            updatedAt: LocalDateTime = LocalDateTime.now(),
        ): BoardsResponse {
            return BoardsResponse(
                id = id,
                title = title,
                username = username,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
        }
    }
}
