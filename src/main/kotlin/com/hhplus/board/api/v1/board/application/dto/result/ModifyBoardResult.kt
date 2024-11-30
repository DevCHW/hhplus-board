package com.hhplus.board.api.v1.board.application.dto.result

class ModifyBoardResult(
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