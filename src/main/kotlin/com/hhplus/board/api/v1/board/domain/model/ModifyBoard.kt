package com.hhplus.board.api.v1.board.domain.model

data class ModifyBoard(
    val boardId: Long,
    val username: String,
    val title: String,
    val content: String,
)
