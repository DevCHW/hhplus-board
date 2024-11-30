package com.hhplus.board.api.v1.board.application

import com.example.ktboard.domain.error.CoreException
import com.example.ktboard.domain.error.ErrorType
import com.hhplus.board.api.v1.board.application.dto.spec.CreateBoardSpec
import com.hhplus.board.api.v1.board.application.dto.spec.ModifyBoardSpec
import com.hhplus.board.api.v1.board.domain.BoardService
import com.hhplus.board.api.v1.board.domain.model.CreateBoard
import com.hhplus.board.api.v1.user.domain.UserService
import com.hhplus.board.api.v1.user.domain.model.CreateUser
import com.hhplus.board.support.AfterDataCleansing
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest
@AfterDataCleansing
class BoardFacadeTest(
    @Autowired private val boardFacade: BoardFacade,
    @Autowired private val userService: UserService,
    @Autowired private val boardService: BoardService
) {

    @Test
    fun `게시글 생성 스펙을 통해 게시글을 저장할 수 있다`() {
        // given
        val user = userService.createUser(CreateUser.fixture())
        val createBoardSpec = CreateBoardSpec.fixture(userId = user.id)

        // when
        val result = boardFacade.createBoard(createBoardSpec)

        // then
        assertThat(result.username).isEqualTo(user.username)
        assertThat(result.userId).isEqualTo(user.id)
        assertThat(result.title).isEqualTo(createBoardSpec.title)
        assertThat(result.content).isEqualTo(createBoardSpec.content)
    }

    @Nested
    @DisplayName("게시글 수정 테스트")
    inner class ModifyBoard {
        @Test
        fun `게시글 수정 스펙을 통해 게시글을 수정할 수 있다`() {
            // given
            val user = userService.createUser(CreateUser.fixture())
            val board = boardService.createBoard(CreateBoard.fixture(
                userId = user.id, username = user.username
            ))

            val modifyBoardSpec = ModifyBoardSpec.fixture(
                boardId = board.id,
                username = "작성자",
                content = "modify content",
                title = "modify title",
                password = board.password
            )

            // when
            val result = boardFacade.modifyBoard(modifyBoardSpec)

            // then
            assertThat(result.username).isEqualTo(modifyBoardSpec.username)
            assertThat(result.title).isEqualTo(modifyBoardSpec.title)
            assertThat(result.content).isEqualTo(modifyBoardSpec.content)
        }

        @Test
        fun `본인이 작성한 게시물이 아니면 수정할 수 없다`() {
            // given
            val user = userService.createUser(CreateUser.fixture())

            val board = boardService.createBoard(CreateBoard.fixture(
                userId = user.id, username = user.username
            ))

            val modifyBoardSpec = ModifyBoardSpec.fixture(
                boardId = board.id,
                userId = 100L,
            )

            // when & then
            assertThatThrownBy {
                boardFacade.modifyBoard(modifyBoardSpec)
            }
                .isInstanceOf(CoreException::class.java)
                .hasMessage("본인이 작성한 게시글만 수정할 수 있습니다.")
        }

        @Test
        fun `게시물 비밀번호가 다르면 수정할 수 없다`() {
            val user = userService.createUser(CreateUser.fixture())
            val board = boardService.createBoard(CreateBoard.fixture(
                userId = user.id, username = user.username, password = "12345"
            ))

            val modifyBoardSpec = ModifyBoardSpec.fixture(
                boardId = board.id,
                userId = user.id,
                password = "1234"
            )

            // when & then
            assertThatThrownBy {
                boardFacade.modifyBoard(modifyBoardSpec)
            }
                .isInstanceOf(CoreException::class.java)
                .hasMessage("비밀번호가 다릅니다.")
        }
    }

    @Nested
    @DisplayName("게시글 삭제 테스트")
    inner class DeleteBoard {
        @Test
        fun `게시글 삭제를 할 수 있다`() {
            // given
            val user = userService.createUser(CreateUser.fixture())
            val board = boardService.createBoard(CreateBoard.fixture(
                userId = user.id, username = user.username
            ))

            // when
            boardFacade.deleteBoard(user.id, board.id)

            // then
            assertThatThrownBy {
                boardService.getBoard(board.id)
            }
                .isInstanceOf(CoreException::class.java)
                .extracting("errorType")
                .isEqualTo(ErrorType.NOT_FOUND)
        }

        @Test
        fun `본인이 작성한 게시글이 아니라면 삭제할 수 없다`() {
            // given
            val user = userService.createUser(CreateUser.fixture())
            val board = boardService.createBoard(CreateBoard.fixture(
                userId = 100L, username = user.username
            ))

            // when & then
            assertThatThrownBy {
                boardFacade.deleteBoard(user.id, board.id)
            }
                .isInstanceOf(CoreException::class.java)
                .hasMessage("본인이 작성한 게시글만 삭제할 수 있습니다.")
        }
    }

    @Test
    fun `게시글 전체 목록을 생성 일자 기준으로 오름차순 정렬하여 조회할 수 있다`() {
        // then
        val user = userService.createUser(CreateUser.fixture())
        val board1 = boardService.createBoard(CreateBoard.fixture(userId = user.id))
        val board2 = boardService.createBoard(CreateBoard.fixture(userId = user.id))
        val board3 = boardService.createBoard(CreateBoard.fixture(userId = user.id))
        val boards = listOf(board1, board2, board3)

        // when
        val result = boardFacade.getBoards()

        // then
        assertThat(result)
            .hasSize(boards.size)
            .extracting("id" ,"title" ,"username" ,"createdAt" ,"updatedAt")
            .containsExactly(
                tuple(board3.id, board3.title, board3.username, board3.createdAt, board3.updatedAt),
                tuple(board2.id, board2.title, board2.username, board2.createdAt, board2.updatedAt),
                tuple(board1.id, board1.title, board1.username, board1.createdAt, board1.updatedAt)
            )
    }

    @Test
    fun `게시글 ID를 통해 게시글을 조회할 수 있다`() {
        // then
        val user = userService.createUser(CreateUser.fixture())
        val board = boardService.createBoard(CreateBoard.fixture(userId = user.id))

        // when
        val result = boardFacade.getBoard(board.id)

        // then
        assertThat(result.id).isEqualTo(board.id)
        assertThat(result.title).isEqualTo(board.title)
        assertThat(result.content).isEqualTo(board.content)
        assertThat(result.username).isEqualTo(board.username)
        assertThat(result.createdAt).isEqualTo(board.createdAt)
        assertThat(result.updatedAt).isEqualTo(board.updatedAt)
    }
}