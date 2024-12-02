package com.hhplus.board.api.v1.board.application.dto.result

import com.hhplus.board.api.v1.board.domain.model.Board

data class CreateBoardResult(
    val id: Long,
    val userId: Long,
    val username: String,
    val title: String,
    val content: String,
) {
    companion object {
        fun from(board: Board): CreateBoardResult {
            return CreateBoardResult(
                id = board.id,
                userId = board.userId,
                username = board.username,
                title = board.title,
                content = board.content,
            )
        }
        fun fixture(
            id: Long = 1L,
            userId: Long = 1L,
            username: String = "username",
            title: String = "title",
            content: String = "content",
        ): CreateBoardResult {
            return CreateBoardResult(
                id = id,
                userId = userId,
                username = username,
                title = title,
                content = content,
            )
        }
    }
}
