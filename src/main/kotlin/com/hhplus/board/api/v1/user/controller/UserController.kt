package com.hhplus.board.api.v1.user.controller

import com.hhplus.board.api.v1.user.application.UserFacade
import com.hhplus.board.api.v1.user.controller.dto.request.UserCreateRequest
import com.hhplus.board.api.v1.user.controller.dto.response.UserCreateResponse
import com.hhplus.board.support.response.ApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userFacade: UserFacade,
) {
    /**
     * 유저 생성
     */
    @PostMapping("/api/v1/users")
    fun createUser(
        @RequestBody request: UserCreateRequest
    ): ApiResponse<UserCreateResponse> {
        val result = userFacade.signUp(request.toSpec())
        return ApiResponse.success(UserCreateResponse.from(result))
    }

}