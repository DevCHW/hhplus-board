package com.hhplus.board.api.v1.user.application.dto.spec

import com.example.ktboard.domain.error.CoreException
import com.example.ktboard.domain.error.ErrorType
import com.hhplus.board.api.v1.user.domain.model.UserCreate
import org.springframework.security.crypto.password.PasswordEncoder

data class CreateUserSpec(
    val username: String,
    val password: String,
) {
    init {
        require(username.length in 4..10) {
           throw CoreException(ErrorType.BAD_REQUEST, "유저 이름은 최소 4자 이상, 10자 이하여야 합니다.")
        }

        require(username.all { it.isLowerCase() || it.isDigit() }) {
            throw CoreException(ErrorType.BAD_REQUEST, "유저 이름은 알파벳 소문자 및 숫자만 가능합니다")
        }

        require(password.length in 8..15) {
            throw CoreException(ErrorType.BAD_REQUEST, "비밀번호는 8자 이상, 15자 이하여야 합니다.")
        }

        require(password.all { it.isLetterOrDigit() }) {
            throw CoreException(ErrorType.BAD_REQUEST, "비밀번호는 알파벳 대소문자 및 숫자만 가능합니다.")
        }
    }

    fun toModel(passwordEncoder: PasswordEncoder): UserCreate {
        return UserCreate(
            username = username,
            password = passwordEncoder.encode(password),
        )
    }
}
