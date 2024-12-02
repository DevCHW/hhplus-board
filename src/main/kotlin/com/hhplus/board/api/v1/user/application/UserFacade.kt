package com.hhplus.board.api.v1.user.application

import com.hhplus.board.api.v1.user.application.dto.spec.SignUpSpec
import com.hhplus.board.api.v1.user.application.dto.result.SignUpResult
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
    fun signUp(signUpSpec: SignUpSpec): SignUpResult {
        val user = userService.createUser(signUpSpec.toModel(passwordEncoder))
        return SignUpResult.from(user)
    }

}
