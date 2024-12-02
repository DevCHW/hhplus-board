package com.hhplus.board.api.v1.board.application.dto.result

import com.hhplus.board.api.v1.board.domain.model.Board

class ModifyBoardResult(
    val id: Long,
    val title: String,
    val content: String,
    val username: String,
) {
    companion object {

        fun from(modifiedBoard: Board): ModifyBoardResult {
            return ModifyBoardResult(
                id = modifiedBoard.id,
                title = modifiedBoard.title,
                content = modifiedBoard.content,
                username = modifiedBoard.username,
            )
        }

        fun fixture(
            id: Long = 1L,
            title: String = "title",
            content: String = "content",
            username: String = "username",
        ): ModifyBoardResult {
            return ModifyBoardResult(
                id = id,
                title = title,
                content = content,
                username = username,
            )
        }
    }
}