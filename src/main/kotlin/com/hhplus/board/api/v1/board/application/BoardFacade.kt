package com.hhplus.board.api.v1.board.application

import com.example.ktboard.domain.error.CoreException
import com.example.ktboard.domain.error.ErrorType
import com.hhplus.board.api.v1.board.application.dto.result.CreateBoardResult
import com.hhplus.board.api.v1.board.application.dto.result.GetBoardResult
import com.hhplus.board.api.v1.board.application.dto.result.GetBoardsResult
import com.hhplus.board.api.v1.board.application.dto.result.ModifyBoardResult
import com.hhplus.board.api.v1.board.application.dto.spec.CreateBoardSpec
import com.hhplus.board.api.v1.board.application.dto.spec.ModifyBoardSpec
import com.hhplus.board.api.v1.board.domain.BoardService
import com.hhplus.board.api.v1.user.domain.UserService
import org.springframework.stereotype.Component

@Component
class BoardFacade(
    private val boardService: BoardService,
    private val userService: UserService,
) {

    /**
     * 게시글 생성
     */
    fun createBoard(createBoardSpec: CreateBoardSpec): CreateBoardResult {
        val user = userService.getUser(createBoardSpec.userId)
        boardService.createBoard(createBoardSpec.toModel(user.username))
        return CreateBoardResult.fixture()
    }

    /**
     * 게시글 수정
     */
    fun modifyBoard(modifyBoardSpec: ModifyBoardSpec): ModifyBoardResult {
        val board = boardService.getBoard(modifyBoardSpec.boardId)

        if (board.password != modifyBoardSpec.password) {
            throw CoreException(ErrorType.SERVER_UNPROCESSABLE, "비밀번호가 다릅니다.")
        }

        boardService.modifyBoard(modifyBoardSpec.toModel())
        return ModifyBoardResult.fixture()
    }

    /**
     * 게시글 삭제
     */
    fun deleteBoard(userId: Long, boardId: Long) {
        boardService.deleteBoard(boardId)
    }

    /**
     * 게시글 목록 조회
     */
    fun getBoards(): List<GetBoardsResult> {
        return boardService.getBoards().map { GetBoardsResult.from(it) }
            .sortedBy { it.createdAt }
            .reversed()
    }

    /**
     * 게시글 단건 조회
     */
    fun getBoard(boardId: Long): GetBoardResult {
        val board = boardService.getBoard(boardId)
        return GetBoardResult.from(board)
    }

}