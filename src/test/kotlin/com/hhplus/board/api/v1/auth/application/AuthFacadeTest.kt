package com.hhplus.board.api.v1.auth.application

import com.example.ktboard.domain.error.CoreException
import com.example.ktboard.domain.error.ErrorType
import com.hhplus.board.api.global.jwt.JwtProvider
import com.hhplus.board.api.global.jwt.TokenInfo
import com.hhplus.board.api.v1.auth.application.dto.spec.LoginSpec
import com.hhplus.board.api.v1.user.domain.UserService
import com.hhplus.board.api.v1.user.domain.model.User
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.springframework.security.crypto.password.PasswordEncoder
import kotlin.test.Test

class AuthFacadeTest {

    private val userService: UserService = mockk()
    private val jwtProvider: JwtProvider = mockk()
    private val passwordEncoder: PasswordEncoder = mockk()
    private val authFacade = AuthFacade(userService, jwtProvider, passwordEncoder)

    @Nested
    @DisplayName("로그인 테스트")
    inner class Login {
        @Test
        fun `로그인 스펙을 통해 로그인 성공 시 로그인 결과를 반환한다`() {
            // given
            val loginSpec = LoginSpec.fixture()
            val loginUser = User.fixture()
            val tokenInfo = TokenInfo.fixture()
            every { userService.getUserByUsername(any()) } returns loginUser
            every { passwordEncoder.matches(any(), any()) } returns true
            every { jwtProvider.generateToken(any()) } returns tokenInfo

            // when
            val result = authFacade.login(loginSpec)

            // then
            assertThat(result.accessToken).isEqualTo(tokenInfo.accessToken)
            assertThat(result.refreshToken).isEqualTo(tokenInfo.refreshToken)
            assertThat(result.expiresIn).isEqualTo(tokenInfo.expiresIn)
            assertThat(result.tokenType).isEqualTo(tokenInfo.tokenType.name)
        }

        @Test
        fun `비밀번호가 다르면 예외가 발생한다`() {
            // given
            val loginSpec = LoginSpec.fixture()
            val loginUser = User.fixture()
            every { userService.getUserByUsername(any()) } returns loginUser
            every { passwordEncoder.matches(any(), any()) } returns false

            // when & then
            assertThatThrownBy { authFacade.login(loginSpec) }
                .isInstanceOf(CoreException::class.java)
                .hasMessage("비밀번호가 일치하지 않습니다.")
                .extracting("errorType").isEqualTo(ErrorType.SERVER_UNPROCESSABLE)

        }
    }
}