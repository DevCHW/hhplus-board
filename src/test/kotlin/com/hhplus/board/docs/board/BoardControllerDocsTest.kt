package com.hhplus.board.docs.board

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import com.hhplus.board.api.v1.board.application.BoardFacade
import com.hhplus.board.api.v1.board.application.dto.result.CreateBoardResult
import com.hhplus.board.api.v1.board.application.dto.result.GetBoardResult
import com.hhplus.board.api.v1.board.application.dto.result.GetBoardsResult
import com.hhplus.board.api.v1.board.application.dto.result.ModifyBoardResult
import com.hhplus.board.api.v1.board.controller.BoardController
import com.hhplus.board.api.v1.board.controller.dto.request.CreateBoardRequest
import com.hhplus.board.api.v1.board.controller.dto.request.ModifyBoardRequest
import com.hhplus.board.support.restdocs.RestDocsTestSupport
import com.hhplus.board.support.restdocs.RestDocsUtils.requestPreprocessor
import com.hhplus.board.support.restdocs.RestDocsUtils.responsePreprocessor
import io.mockk.every
import io.mockk.mockk
import io.restassured.http.ContentType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.restdocs.payload.JsonFieldType.*
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters

class BoardControllerDocsTest : RestDocsTestSupport() {
    private lateinit var boardController: BoardController
    private lateinit var boardFacade: BoardFacade

    @BeforeEach
    fun setup() {
        boardFacade = mockk()
        boardController = BoardController(boardFacade)
        mockMvc = mockController(boardController)
    }

    @Test
    fun `게시글 생성 API 문서 테스트`() {
        val request = CreateBoardRequest.fixture()
        every { boardFacade.createBoard(any()) } returns CreateBoardResult.fixture()

        given()
            .header(HttpHeaders.AUTHORIZATION, EXAMPLE_BEARER_TOKEN)
            .body(request)
            .contentType(ContentType.JSON)
            .post("/api/v1/boards")
            .then().log().all()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "게시글 생성",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    requestFields(
                        fieldWithPath("title").type(STRING).description("게시글 제목"),
                        fieldWithPath("content").type(STRING).description("게시글 내용"),
                        fieldWithPath("password").type(STRING).description("비밀번호"),
                    ),
                    responseFields(
                        fieldWithPath("result").type(STRING).description("결과 타입 (SUCCESS / ERROR"),
                        fieldWithPath("data").type(OBJECT).description("데이터"),
                        fieldWithPath("data.id").type(NUMBER).description("게시글 ID"),
                        fieldWithPath("data.userId").type(NUMBER).description("유저 ID"),
                        fieldWithPath("data.username").type(STRING).description("작성자 이름"),
                        fieldWithPath("data.title").type(STRING).description("게시글 제목"),
                        fieldWithPath("data.content").type(STRING).description("게시글 내용"),
                    ),
                ),
            )
    }

    @Test
    fun `게시글 수정 API 문서 테스트`() {
        val boardId = 1L
        val request = ModifyBoardRequest.fixture()
        every { boardFacade.modifyBoard(any()) } returns ModifyBoardResult.fixture()

        given()
            .header(HttpHeaders.AUTHORIZATION, EXAMPLE_BEARER_TOKEN)
            .body(request)
            .contentType(ContentType.JSON)
            .put("/api/v1/boards/{boardId}", boardId.toString())
            .then().log().all()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "게시글 수정",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    pathParameters(
                        parameterWithName("boardId").description("게시글 ID")
                    ),
                    requestFields(
                        fieldWithPath("title").type(STRING).description("게시글 제목"),
                        fieldWithPath("content").type(STRING).description("게시글 내용"),
                        fieldWithPath("username").type(STRING).description("작성자명"),
                        fieldWithPath("password").type(STRING).description("비밀번호"),
                    ),
                    responseFields(
                        fieldWithPath("result").type(STRING).description("결과 타입 (SUCCESS / ERROR"),
                        fieldWithPath("data").type(OBJECT).description("데이터"),
                        fieldWithPath("data.id").type(NUMBER).description("게시글 ID"),
                        fieldWithPath("data.username").type(STRING).description("작성자 이름"),
                        fieldWithPath("data.title").type(STRING).description("게시글 제목"),
                        fieldWithPath("data.content").type(STRING).description("게시글 내용"),
                    ),
                ),
            )
    }

    @Test
    fun `게시글 삭제 API 문서 테스트`() {
        val boardId = 1L
        every { boardFacade.deleteBoard(any(), any()) } returns Unit

        given()
            .header(HttpHeaders.AUTHORIZATION, EXAMPLE_BEARER_TOKEN)
            .contentType(ContentType.JSON)
            .delete("/api/v1/boards/{boardId}", boardId.toString())
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "게시글 삭제",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    pathParameters(
                        parameterWithName("boardId").description("게시글 ID")
                    ),
                    responseFields(
                        fieldWithPath("result").type(STRING).description("결과 타입 (SUCCESS / ERROR"),
                        fieldWithPath("data").type(OBJECT).description("데이터"),
                    ),
                ),
            )
    }

    @Test
    fun `게시글 목록 조회 API 문서 테스트`() {
        val result1 = GetBoardsResult.fixture(id = 1L)
        val result2 = GetBoardsResult.fixture(id = 2L)
        val result3 = GetBoardsResult.fixture(id = 3L)
        val result = listOf(result1, result2, result3)

        every { boardFacade.getBoards() } returns result

        given()
            .contentType(ContentType.JSON)
            .get("/api/v1/boards")
            .then().log().all()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "게시글 목록 조회",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    responseFields(
                        fieldWithPath("result").type(STRING).description("결과 타입 (SUCCESS / ERROR"),
                        fieldWithPath("data[]").type(ARRAY).description("데이터"),
                        fieldWithPath("data[].id").type(NUMBER).description("게시글 ID"),
                        fieldWithPath("data[].title").type(STRING).description("게시글 제목"),
                        fieldWithPath("data[].username").type(STRING).description("작성자 명"),
                        fieldWithPath("data[].createdAt").type(STRING).description("생성 시점"),
                        fieldWithPath("data[].updatedAt").type(STRING).description("마지막 업데이트 시점"),
                    ),
                ),
            )
    }

    @Test
    fun `게시글 단건 조회 API 문서 테스트`() {
        val boardId = 1L
        every { boardFacade.getBoard(any()) } returns GetBoardResult.fixture()

        given()
            .contentType(ContentType.JSON)
            .get("/api/v1/boards/{boardId}", boardId.toString())
            .then().log().all()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "게시글 단건 조회",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    pathParameters(
                        parameterWithName("boardId").description("게시글 ID")
                    ),
                    responseFields(
                        fieldWithPath("result").type(STRING).description("결과 타입 (SUCCESS / ERROR"),
                        fieldWithPath("data").type(OBJECT).description("데이터"),
                        fieldWithPath("data.id").type(NUMBER).description("게시글 ID"),
                        fieldWithPath("data.title").type(STRING).description("게시글 제목"),
                        fieldWithPath("data.content").type(STRING).description("게시글 내용"),
                        fieldWithPath("data.username").type(STRING).description("작성자 명"),
                        fieldWithPath("data.createdAt").type(STRING).description("생성 시점"),
                        fieldWithPath("data.updatedAt").type(STRING).description("마지막 업데이트 시점"),
                    ),
                ),
            )
    }


}