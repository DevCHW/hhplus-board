package com.hhplus.board.api.v1.board.domain

import com.hhplus.board.api.v1.board.domain.model.CreateBoard
import com.hhplus.board.api.v1.board.domain.model.ModifyBoard
import com.hhplus.board.storage.core.entity.BoardEntity
import com.hhplus.board.storage.core.repository.BoardEntityJpaRepository
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull

class BoardServiceTest {
    private val boardEntityJpaRepository: BoardEntityJpaRepository = mockk()
    private val boardService: BoardService = BoardService(boardEntityJpaRepository)

    @Test
    fun `게시글 엔티티를 조회하여 게시글 모델로 변환하여 반환한다`() {
        // given
        val boardEntity = BoardEntity.fixture()
        every { boardEntityJpaRepository.findByIdOrNull(any()) } returns boardEntity

        // when
        val board = boardService.getBoard(boardEntity.id)

        // then
        assertThat(boardEntity.id).isEqualTo(board.id)
        assertThat(boardEntity.title).isEqualTo(board.title)
        assertThat(boardEntity.content).isEqualTo(board.content)
        assertThat(boardEntity.username).isEqualTo(board.username)
        assertThat(boardEntity.password).isEqualTo(board.password)
    }

    @Test
    fun `게시글 엔티티 목록을 조회하여 게시글 모델 목록으로 변환하여 반환한다`() {
        // given
        val boardEntity1 = BoardEntity.fixture()
        val boardEntity2 = BoardEntity.fixture()
        val boardEntities = listOf(boardEntity1, boardEntity2)
        every { boardEntityJpaRepository.findAll() } returns boardEntities

        // when
        val boards = boardService.getBoards()

        // then
        assertThat(boards)
            .hasSize(boardEntities.size)
            .extracting("id", "title", "content")
            .containsExactly(
                tuple(boardEntity1.id, boardEntity1.title, boardEntity1.content),
                tuple(boardEntity2.id, boardEntity1.title, boardEntity1.content),
            )
    }

    @Test
    fun `게시글 생성 모델로 게시글을 생성할 수 있다`() {
        // given
        val boardEntity = BoardEntity.fixture()
        every { boardEntityJpaRepository.save(any()) } returns boardEntity
        val createBoard = CreateBoard.fixture(
            userId = boardEntity.userId,
            title = boardEntity.title,
            content = boardEntity.content,
        )

        // when
        val saved = boardService.createBoard(createBoard)

        // then
        assertThat(saved.title).isEqualTo(createBoard.title)
        assertThat(saved.content).isEqualTo(createBoard.content)
        assertThat(saved.username).isEqualTo(createBoard.username)
    }

    @Test
    fun `게시글 수정 모델로 게시글을 수정할 수 있다`() {
        // given
        val boardEntity = BoardEntity.fixture()
        every { boardEntityJpaRepository.findByIdOrNull(any()) } returns boardEntity

        val modifyBoard = ModifyBoard.fixture(
            boardId = boardEntity.id,
            title = "수정된 게시글 제목",
            content = "수정된 게시글 내용",
            username = "수정된 유저 이름",
        )

        // when
        val result = boardService.modifyBoard(modifyBoard)

        // then
        assertThat(result.id).isEqualTo(modifyBoard.boardId)
        assertThat(result.title).isEqualTo(modifyBoard.title)
        assertThat(result.content).isEqualTo(modifyBoard.content)
        assertThat(result.username).isEqualTo(modifyBoard.username)
    }

}