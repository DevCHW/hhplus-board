package com.hhplus.board.api.v1.board.application.dto.spec

import com.hhplus.board.api.v1.board.domain.model.ModifyBoard

data class ModifyBoardSpec(
    val boardId: Long,
    val userId: Long,
    val username: String,
    val title: String,
    val content: String,
    val password: String,
) {
    fun toModel(): ModifyBoard {
        return ModifyBoard(
            boardId = boardId,
            username = username,
            title = title,
            content = content,
        )
    }

    companion object {
        fun fixture(
            boardId: Long = 1L,
            userId: Long = 1L,
            username: String = "username",
            title: String = "title",
            content: String = "content",
            password: String = "1234",
        ): ModifyBoardSpec {
            return ModifyBoardSpec(
                boardId = boardId,
                username = username,
                userId = userId,
                title = title,
                content = content,
                password = password,
            )
        }
    }
}
