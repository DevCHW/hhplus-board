package com.hhplus.board.api.v1.board.controller.dto.response

import com.hhplus.board.api.v1.board.application.dto.result.ModifyBoardResult

data class ModifyBoardResponse(
    val id: Long,
    val title: String,
    val content: String,
    val username: String,
) {
    companion object {
        fun fixture(
            id: Long = 1L,
            title: String = "title",
            content: String = "content",
            username: String = "username",
        ): ModifyBoardResponse {
            return ModifyBoardResponse(
                id = id,
                title = title,
                content = content,
                username = username,
            )
        }

        fun from(result: ModifyBoardResult): ModifyBoardResponse {
            return ModifyBoardResponse(
                id = result.id,
                title = result.title,
                content = result.content,
                username = result.username,
            )
        }
    }
}
