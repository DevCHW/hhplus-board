package com.hhplus.board.api.v1.board.controller.dto.response

import com.hhplus.board.api.v1.board.application.dto.result.GetBoardResult
import java.time.LocalDateTime

data class BoardResponse(
    val id: Long,
    val title: String,
    val content: String,
    val username: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(result: GetBoardResult): BoardResponse {
            return BoardResponse(
                id = result.id,
                title = result.title,
                content = result.content,
                username = result.username,
                createdAt = result.createdAt,
                updatedAt = result.updatedAt,
            )
        }
    }
}
