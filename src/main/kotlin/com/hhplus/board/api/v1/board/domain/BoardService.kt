package com.hhplus.board.api.v1.board.domain

import com.hhplus.board.api.v1.board.domain.model.Board
import com.hhplus.board.api.v1.board.domain.model.CreateBoard
import com.hhplus.board.api.v1.board.domain.model.ModifyBoard
import com.hhplus.board.storage.core.entity.BoardEntity
import com.hhplus.board.storage.core.repository.BoardEntityJpaRepository
import com.hhplus.board.utils.notFountThrow
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardService(
    private val boardEntityJpaRepository: BoardEntityJpaRepository,
) {

    /**
     * 게시글 단건 조회
     */
    fun getBoard(boardId: Long): Board {
        val boardEntity = boardEntityJpaRepository.findByIdOrNull(boardId)?: notFountThrow()
        return Board.from(boardEntity)
    }

    /**
     * 게시글 목록 조회
     */
    fun getBoards(): List<Board> {
        val boardEntities = boardEntityJpaRepository.findAll()
        return boardEntities.map { Board.from(it) }
    }

    /**
     * 게시글 생성
     */
    fun createBoard(createBoard: CreateBoard): Board {
        val boardEntity = boardEntityJpaRepository.save(createBoard.toEntity())
        return Board.from(boardEntity)
    }

    /**
     * 게시글 수정
     */
    @Transactional
    fun modifyBoard(modifyBoard: ModifyBoard): Board {
        val boardEntity = boardEntityJpaRepository.findByIdOrNull(modifyBoard.boardId) ?: notFountThrow()
        boardEntity.modify(BoardEntity.ModifyBoardEntity(
            title = modifyBoard.title,
            content = modifyBoard.content,
            username = modifyBoard.username,
        ))
        return Board.from(boardEntity)
    }

    /**
     * 게시글 삭제
     */
    fun deleteBoard(boardId: Long) {
        boardEntityJpaRepository.deleteById(boardId)
    }
}