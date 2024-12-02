package com.hhplus.board.api.v1.board.controller.dto.response

import com.hhplus.board.api.v1.board.application.dto.result.CreateBoardResult

data class CreateBoardResponse(
    val id: Long,
    val userId: Long,
    val username: String,
    val title: String,
    val content: String,
) {
    companion object {
        fun from(result: CreateBoardResult): CreateBoardResponse {
            return CreateBoardResponse(
                id = result.id,
                userId = result.userId,
                username = result.username,
                title = result.title,
                content = result.content,
            )
        }

        fun fixture(
            id: Long = 1L,
            userId: Long = 1L,
            username: String = "username",
            title: String = "title",
            content: String = "content",
        ): CreateBoardResponse {
            return CreateBoardResponse(
                id = id,
                userId = userId,
                username = username,
                title = title,
                content = content,
            )
        }
    }
}