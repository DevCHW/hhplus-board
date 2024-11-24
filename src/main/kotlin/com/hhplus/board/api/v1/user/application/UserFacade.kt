package com.hhplus.board.api.v1.user.application

import com.hhplus.board.api.v1.user.application.dto.spec.CreateUserSpec
import com.hhplus.board.api.v1.user.application.dto.result.CreateUserResult
import com.hhplus.board.api.v1.user.domain.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserFacade(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder
) {

    /**
     * 회원가입
     */
    fun signUp(createUserSpec: CreateUserSpec): CreateUserResult {
        val user = userService.create(createUserSpec.toModel(passwordEncoder))
        return CreateUserResult.from(user)
    }

}
