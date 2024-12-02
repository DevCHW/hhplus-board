package com.hhplus.board.docs.user

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import com.hhplus.board.api.v1.user.application.UserFacade
import com.hhplus.board.api.v1.user.application.dto.result.SignUpResult
import com.hhplus.board.api.v1.user.controller.UserController
import com.hhplus.board.api.v1.user.controller.dto.request.UserCreateRequest
import com.hhplus.board.support.restdocs.RestDocsTestSupport
import com.hhplus.board.support.restdocs.RestDocsUtils.requestPreprocessor
import com.hhplus.board.support.restdocs.RestDocsUtils.responsePreprocessor
import io.mockk.every
import io.mockk.mockk
import io.restassured.http.ContentType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.payload.JsonFieldType.*
import org.springframework.restdocs.payload.PayloadDocumentation.*

class UserControllerDocsTest : RestDocsTestSupport() {
    private lateinit var userController: UserController
    private lateinit var userFacade: UserFacade

    @BeforeEach
    fun setUp() {
        userFacade = mockk()
        userController = UserController(userFacade)
        mockMvc = mockController(userController)
    }

    @Test
    fun `유저 생성 API 문서 테스트`() {
        val request = UserCreateRequest.fixture()
        every { userFacade.signUp(any()) } returns SignUpResult.fixture();

        given()
            .body(request)
            .contentType(ContentType.JSON)
            .post("/api/v1/users")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "create-user",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    requestFields(
                        fieldWithPath("username").type(STRING).description("유저이름"),
                        fieldWithPath("password").type(STRING).description("비밀번호"),
                    ),
                    responseFields(
                        fieldWithPath("result").type(STRING).description("결과 타입 (SUCCESS / ERROR"),
                        fieldWithPath("data").type(OBJECT).description("데이터"),
                        fieldWithPath("data.id").type(NUMBER).description("유저 ID"),
                        fieldWithPath("data.username").type(STRING).description("유저이름"),
                        fieldWithPath("data.createdAt").type(STRING).description("생성시점"),
                        fieldWithPath("data.updatedAt").type(STRING).description("마지막 업데이트 시점"),
                        fieldWithPath("data.resultMessage").type(STRING).description("결과 메세지"),
                    ),
                ),
            )
    }

}