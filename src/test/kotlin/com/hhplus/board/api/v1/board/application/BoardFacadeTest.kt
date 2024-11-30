package com.hhplus.board.api.v1.board.application

import com.hhplus.board.api.v1.board.application.dto.spec.CreateBoardSpec
import com.hhplus.board.api.v1.board.domain.BoardService
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest
class BoardFacadeTest(
    @Autowired val boardFacade: BoardFacade,
    @Autowired var boardService: BoardService
) {


//    @Test
//    fun `게시글 생성 스펙을 통해 게시글을 저장하고, 작성된 게시물을 반환한다.`() {
//        // given
//        val spec = CreateBoardSpec.fixture()
//
//        // when
//        val result = boardFacade.createBoard(spec)
//        boardService.getBoard()
//
//        // then
//        assertThat(result.id).isEqualTo(spec.id)
//
//    }

}