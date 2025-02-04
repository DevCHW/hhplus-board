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
        val board = boardService.createBoard(createBoardSpec.toModel(user.username))
        return CreateBoardResult.from(board)
    }

    /**
     * 게시글 수정
     */
    fun modifyBoard(modifyBoardSpec: ModifyBoardSpec): ModifyBoardResult {
        val board = boardService.getBoard(modifyBoardSpec.boardId)

        if (board.userId != modifyBoardSpec.userId) {
            throw CoreException(ErrorType.SERVER_UNPROCESSABLE, "본인이 작성한 게시글만 수정할 수 있습니다.")
        }

        if (board.password != modifyBoardSpec.password) {
            throw CoreException(ErrorType.SERVER_UNPROCESSABLE, "비밀번호가 다릅니다.")
        }

        val modifiedBoard = boardService.modifyBoard(modifyBoardSpec.toModel())
        return ModifyBoardResult.from(modifiedBoard)
    }

    /**
     * 게시글 삭제
     */
    fun deleteBoard(userId: Long, boardId: Long) {
        val board = boardService.getBoard(boardId)

        if (userId != board.userId) {
            throw CoreException(ErrorType.SERVER_UNPROCESSABLE, "본인이 작성한 게시글만 삭제할 수 있습니다.")
        }

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