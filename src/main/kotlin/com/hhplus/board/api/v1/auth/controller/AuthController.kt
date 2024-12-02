package com.hhplus.board.api.v1.auth.controller

import com.hhplus.board.api.v1.auth.application.AuthFacade
import com.hhplus.board.api.v1.auth.controller.dto.request.LoginRequest
import com.hhplus.board.api.v1.auth.controller.dto.response.LoginResponse
import com.hhplus.board.support.response.ApiResponse
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.http.HttpHeaders.*
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val authFacade: AuthFacade,
) {

    /**
     * 로그인
     */
    @PostMapping("/api/v1/auth/login")
    fun login(
        @RequestBody @Valid request: LoginRequest,
        servletResponse: HttpServletResponse,
    ): ApiResponse<LoginResponse> {
        val result = authFacade.login(request.toSpec())
        servletResponse.setHeader(AUTHORIZATION, "${result.tokenType} ${result.accessToken}")
        return ApiResponse.success(LoginResponse.from(result))
    }
}
