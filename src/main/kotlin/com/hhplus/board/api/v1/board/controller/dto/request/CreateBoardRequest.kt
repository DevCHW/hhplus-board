package com.hhplus.board.api.v1.board.controller.dto.request

import com.hhplus.board.api.v1.board.application.dto.spec.CreateBoardSpec

data class CreateBoardRequest(
    val title: String,
    val content: String,
    val password: String,
) {
    fun toSpec(userId: Long): CreateBoardSpec {
        return CreateBoardSpec(
            userId = userId,
            title = title,
            content = content,
            password = password,
        )
    }

    companion object {
        fun fixture(
            title: String = "example title",
            content: String = "example content",
            password: String = "1234"
        ): CreateBoardRequest {
            return CreateBoardRequest(
                title = title,
                content = content,
                password = password,
            )
        }
    }
}
