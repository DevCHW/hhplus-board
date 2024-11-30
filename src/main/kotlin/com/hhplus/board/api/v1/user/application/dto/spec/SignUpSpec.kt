package com.hhplus.board.api.v1.user.application.dto.spec

import com.example.ktboard.domain.error.CoreException
import com.example.ktboard.domain.error.ErrorType
import com.hhplus.board.api.v1.user.domain.model.CreateUser
import org.springframework.security.crypto.password.PasswordEncoder

data class SignUpSpec(
    val username: String,
    val password: String,
) {
    init {
        require(username.length in 4..10) {
           throw CoreException(
               errorType = ErrorType.VALIDATION_ERROR,
               errorMessage = "유저 이름은 최소 4자 이상, 10자 이하여야 합니다."
           )
        }

        require(username.all { it.isLowerCase() || it.isDigit() }) {
            throw CoreException(
                errorType = ErrorType.VALIDATION_ERROR,
                errorMessage = "유저 이름은 알파벳 소문자 및 숫자만 가능합니다"
            )
        }

        require(password.length in 8..15) {
            throw CoreException(
                errorType = ErrorType.VALIDATION_ERROR,
                errorMessage = "비밀번호는 8자 이상, 15자 이하여야 합니다."
            )
        }

        require(password.all { it.isLetterOrDigit() }) {
            throw CoreException(
                errorType = ErrorType.VALIDATION_ERROR,
                errorMessage = "비밀번호는 알파벳 대소문자 및 숫자만 가능합니다."
            )
        }
    }

    fun toModel(passwordEncoder: PasswordEncoder): CreateUser {
        return CreateUser(
            username = username,
            password = passwordEncoder.encode(password),
        )
    }

    companion object {
        fun fixture(
            username: String = "user123",
            password: String = "password123",
        ): SignUpSpec {
            return SignUpSpec(
                username = username,
                password = password,
            )
        }
    }
}
