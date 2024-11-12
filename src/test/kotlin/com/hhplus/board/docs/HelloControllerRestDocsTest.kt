package com.hhplus.board.docs

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document
import com.hhplus.board.support.HelloController
import com.hhplus.board.support.restdocs.RestDocsTestSupport
import com.hhplus.board.support.restdocs.RestDocsUtils.requestPreprocessor
import com.hhplus.board.support.restdocs.RestDocsUtils.responsePreprocessor
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.restdocs.payload.JsonFieldType.STRING
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields

class HelloControllerRestDocsTest : RestDocsTestSupport() {
    private lateinit var helloController: HelloController

    @BeforeEach
    fun setUp() {
        helloController = HelloController()
        mockMvc = mockController(helloController)
    }

    @DisplayName("RestDocs 문서 동작 테스트")
    @Test
    fun hello() {
        given()
            .get("hello")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .apply(
                document("hello",
                    requestPreprocessor(),
                    responsePreprocessor(),
                    responseFields(
                        fieldWithPath("message").type(STRING).description("메세지"),
                    )
                )
            )
    }
}