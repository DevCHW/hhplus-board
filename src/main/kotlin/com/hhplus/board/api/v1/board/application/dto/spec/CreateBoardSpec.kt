package com.hhplus.board.api.v1.board.application.dto.spec

import com.hhplus.board.api.v1.board.domain.model.CreateBoard

data class CreateBoardSpec(
    val userId: Long,
    val title: String,
    val content: String,
    val password: String,
) {
    fun toModel(username: String): CreateBoard {
        return CreateBoard(
            userId = userId,
            title = title,
            content = content,
            password = password,
            username = username
        )
    }

    companion object {
        fun fixture(
            userId: Long = 1L,
            title: String = "title",
            content: String = "content",
            password: String = "1234",
        ): CreateBoardSpec {
            return CreateBoardSpec(
                userId = userId,
                title = title,
                content = content,
                password = password,
            )
        }
    }
}