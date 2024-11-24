package com.hhplus.board.api.v1.user.application.dto.spec

import com.example.ktboard.domain.error.CoreException
import com.example.ktboard.domain.error.ErrorType
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class SignUpSpecTest {

    @Nested
    @DisplayName("회원가입 스펙 초기화 테스트")
    inner class SignUpSpecInit {
        @Test
        fun `유저 이름이 4자 이하일 경우 예외가 발생한다`() {
            val exception = assertThrows<CoreException> {
                SignUpSpec.fixture(
                    username = "usr"
                )
            }
            assertThat(exception).isInstanceOf(CoreException::class.java)
            assertEquals(ErrorType.VALIDATION_ERROR, exception.errorType)
            assertEquals("유저 이름은 최소 4자 이상, 10자 이하여야 합니다.", exception.message)
        }

        @Test
        fun `유저 이름이 10자 이상일 경우 예외가 발생한다`() {
            val exception = assertThrows<CoreException> {
                SignUpSpec.fixture(
                    username = "username123"
                )
            }

            assertThat(exception).isInstanceOf(CoreException::class.java)
            assertEquals(ErrorType.VALIDATION_ERROR, exception.errorType)
            assertEquals("유저 이름은 최소 4자 이상, 10자 이하여야 합니다.", exception.message)
        }

        @Test
        fun `유저 이름에 특수문자가 있는 경우 예외가 발생한다`() {
            val exception = assertThrows<CoreException> {
                SignUpSpec.fixture(
                    username = "user@123",
                )
            }
            assertThat(exception).isInstanceOf(CoreException::class.java)
            assertEquals(ErrorType.VALIDATION_ERROR, exception.errorType)
            assertEquals("유저 이름은 알파벳 소문자 및 숫자만 가능합니다", exception.message)
        }

        @Test
        fun `유저 이름에 대문자가 있는 경우 예외가 발생한다`() {
            val exception = assertThrows<CoreException> {
                SignUpSpec.fixture(
                    username = "User123",
                )
            }
            assertThat(exception).isInstanceOf(CoreException::class.java)
            assertEquals(ErrorType.VALIDATION_ERROR, exception.errorType)
            assertEquals("유저 이름은 알파벳 소문자 및 숫자만 가능합니다", exception.message)
        }

        @Test
        fun `비밀번호가 8자 이하일 경우 예외가 발생한다`() {
            val exception = assertThrows<CoreException> {
                SignUpSpec.fixture(
                    password = "pass123",
                )
            }
            assertThat(exception).isInstanceOf(CoreException::class.java)
            assertEquals(ErrorType.VALIDATION_ERROR, exception.errorType)
            assertEquals("비밀번호는 8자 이상, 15자 이하여야 합니다.", exception.message)
        }

        @Test
        fun `비밀번호가 15자 이상일 경우 예외가 발생한다`() {
            val exception = assertThrows<CoreException> {
                SignUpSpec.fixture(
                    password = "password12345678"
                )
            }
            assertThat(exception).isInstanceOf(CoreException::class.java)
            assertEquals(ErrorType.VALIDATION_ERROR, exception.errorType)
            assertEquals("비밀번호는 8자 이상, 15자 이하여야 합니다.", exception.message)
        }

        @Test
        fun `비밀번호에 특수문자가 있는 경우 예외가 발생한다`() {
            val exception = assertThrows<CoreException> {
                SignUpSpec.fixture(
                    password = "Password@123"
                )
            }

            assertThat(exception).isInstanceOf(CoreException::class.java)
            assertEquals(ErrorType.VALIDATION_ERROR, exception.errorType)
            assertEquals("비밀번호는 알파벳 대소문자 및 숫자만 가능합니다.", exception.message)
        }
    }

    @Test
    fun `유저 생성 스펙을 통해 유저 생성 모델로 변환할 수 있다`() {
        // given
        val createUserSpec = SignUpSpec.fixture()
        val passwordEncoder = BCryptPasswordEncoder()

        // when
        val result = createUserSpec.toModel(passwordEncoder)

        // then
        assertThat(result.username).isEqualTo(createUserSpec.username)
        assertThat(passwordEncoder.matches(createUserSpec.password, result.password)).isTrue()
    }

}
