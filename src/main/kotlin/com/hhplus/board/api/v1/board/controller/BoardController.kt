package com.hhplus.board.api.v1.board.controller

import com.hhplus.board.api.global.argumentresolver.Login
import com.hhplus.board.api.global.argumentresolver.LoginUser
import com.hhplus.board.api.v1.board.application.BoardFacade
import com.hhplus.board.api.v1.board.controller.dto.request.CreateBoardRequest
import com.hhplus.board.api.v1.board.controller.dto.request.ModifyBoardRequest
import com.hhplus.board.api.v1.board.controller.dto.response.BoardResponse
import com.hhplus.board.api.v1.board.controller.dto.response.BoardsResponse
import com.hhplus.board.api.v1.board.controller.dto.response.CreateBoardResponse
import com.hhplus.board.api.v1.board.controller.dto.response.ModifyBoardResponse
import com.hhplus.board.support.response.ApiResponse
import org.springframework.web.bind.annotation.*

@RestController
class BoardController(
    private val boardFacade: BoardFacade
) {

    /**
     * 게시글 생성
     */
    @PostMapping("/api/v1/boards")
    fun createBoard(
        @RequestBody request: CreateBoardRequest,
        @Login loginUser: LoginUser,
    ): ApiResponse<CreateBoardResponse> {
        val result = boardFacade.createBoard(request.toSpec(loginUser.id))
        return ApiResponse.success(CreateBoardResponse.from(result))
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/api/v1/boards/{boardId}")
    fun modifyBoard(
        @RequestBody request: ModifyBoardRequest,
        @PathVariable boardId: Long,
        @Login loginUser: LoginUser,
    ): ApiResponse<ModifyBoardResponse> {
        val result = boardFacade.modifyBoard(request.toSpec(loginUser.id, boardId))
        return ApiResponse.success(ModifyBoardResponse.from(result))
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/api/v1/boards/{boardId}")
    fun deleteBoard(
        @Login loginUser: LoginUser,
        @PathVariable boardId: Long,
    ): ApiResponse<Unit> {
        val result = boardFacade.deleteBoard(loginUser.id, boardId)
        return ApiResponse.success(result)
    }

    /**
     * 게시글 목록 조회
     */
    @GetMapping("/api/v1/boards")
    fun getBoards(): ApiResponse<List<BoardsResponse>> {
        val result = boardFacade.getBoards()
        return ApiResponse.success(result
            .map { BoardsResponse.from(it) }
            .toList()
        )
    }

    /**
     * 게시글 목록 조회
     */
    @GetMapping("/api/v1/boards/{boardId}")
    fun getBoard(
        @PathVariable boardId: Long
    ): ApiResponse<BoardResponse> {
        val result = boardFacade.getBoard(boardId)
        return ApiResponse.success(BoardResponse.from(result))
    }

}