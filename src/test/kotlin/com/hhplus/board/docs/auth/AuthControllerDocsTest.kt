package com.hhplus.board.docs.auth

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import com.hhplus.board.api.v1.auth.application.AuthFacade
import com.hhplus.board.api.v1.auth.application.dto.result.LoginResult
import com.hhplus.board.api.v1.auth.controller.AuthController
import com.hhplus.board.api.v1.auth.controller.dto.request.LoginRequest
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

class AuthControllerDocsTest : RestDocsTestSupport() {

    private lateinit var authController: AuthController
    private lateinit var authFacade: AuthFacade

    @BeforeEach
    fun setUp() {
        authFacade = mockk()
        authController = AuthController(authFacade)
        mockMvc = mockController(authController)
    }

    @Test
    fun `로그인 API 문서 테스트`() {
        val request = LoginRequest.fixture()
        every { authFacade.login(any()) } returns LoginResult.fixture();

        given()
            .body(request)
            .contentType(ContentType.JSON)
            .post("/api/v1/auth/login")
            .then()
            .status(HttpStatus.OK)
            .apply(
                document(
                    "auth-login",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    requestFields(
                        fieldWithPath("username").type(STRING).description("유저이름"),
                        fieldWithPath("password").type(STRING).description("비밀번호"),
                    ),
                    responseFields(
                        fieldWithPath("result").type(STRING).description("결과 타입 (SUCCESS / ERROR"),
                        fieldWithPath("data").type(OBJECT).description("데이터"),
                        fieldWithPath("data.accessToken").type(STRING).description("엑세스 토큰"),
                        fieldWithPath("data.refreshToken").type(STRING).description("리프레쉬 토큰"),
                        fieldWithPath("data.tokenType").type(STRING).description("토큰 타입(Bearer)"),
                        fieldWithPath("data.expiresIn").type(NUMBER).description("엑세스 토큰 만료 시간"),
                        fieldWithPath("data.message").type(STRING).description("메세지"),
                    ),
                ),
            )
    }

}