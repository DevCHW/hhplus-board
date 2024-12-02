package com.hhplus.board.api.v1.board.controller.dto.request

import com.hhplus.board.api.v1.board.application.dto.spec.ModifyBoardSpec

data class ModifyBoardRequest(
    val title: String,
    val content: String,
    val username: String,
    val password: String,
) {
    fun toSpec(
        userId: Long,
        boardId: Long,
    ): ModifyBoardSpec {
        return ModifyBoardSpec(
            boardId = boardId,
            userId = userId,
            title = title,
            content = content,
            password = password,
            username = username,
        )
    }

    companion object {
        fun fixture(
            title: String = "title",
            content: String = "content",
            username: String = "username",
            password: String = "1234",
        ): ModifyBoardRequest {
            return ModifyBoardRequest(
                title = title,
                content = content,
                username = username,
                password = password,
            )
        }
    }
}