package com.hhplus.board.api.v1.board.domain.model

data class ModifyBoard(
    val boardId: Long,
    val username: String,
    val title: String,
    val content: String,
) {
    companion object {
        fun fixture(
            boardId: Long = 1L,
            username: String = "username",
            title: String = "title",
            content: String = "content",
        ): ModifyBoard {
            return ModifyBoard(
                boardId = boardId,
                username = username,
                title = title,
                content = content,
            )

        }
    }
}
