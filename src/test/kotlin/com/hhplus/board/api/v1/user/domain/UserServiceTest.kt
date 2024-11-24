package com.hhplus.board.api.v1.user.domain

import com.example.ktboard.domain.error.CoreException
import com.example.ktboard.domain.error.ErrorType
import com.hhplus.board.api.v1.user.domain.model.UserCreate
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class UserServiceTest(
    @Autowired val userService: UserService,
) {
    @Test
    fun `유저 생성 모델을 통해 유저를 생성할 수 있다`() {
        // given
        val userCreate = UserCreate.fixture()

        // when
        val createdUser = userService.create(userCreate)
        val user = userService.getUser(createdUser.id)

        // then
        assertThat(createdUser).isEqualTo(user)
    }

    @Nested
    @DisplayName("유저 이름으로 유저 조회 테스트")
    inner class GetUserByUsername {
        @Test
        fun `유저 이름을 통해 유저를 조회할 수 있다`() {
            // given
            val userCreate = UserCreate.fixture()
            val createdUser = userService.create(userCreate)

            // when
            val user = userService.getUserByUsername(createdUser.username)

            // then
            assertThat(createdUser).isEqualTo(user)
        }

        @Test
        fun `유저 이름을 통해 조회된 유저가 없다면 예외가 발생한다`() {
            // when & then
            assertThatThrownBy {
                userService.getUserByUsername("hello")
            }
            .isInstanceOf(CoreException::class.java)
            .hasMessage(ErrorType.NOT_FOUND.message)
            .extracting("errorType").isEqualTo(ErrorType.NOT_FOUND)
        }
    }

    @Nested
    @DisplayName("유저 ID로 유저 조회 테스트")
    inner class GetUser {
        @Test
        fun `유저 ID를 통해 유저를 조회할 수 있다`() {
            val userCreate = UserCreate.fixture()
            val createdUser = userService.create(userCreate)
            val user = userService.getUser(createdUser.id)
            assertThat(createdUser).isEqualTo(user)
        }

        @Test
        fun `유저 ID를 통해 조회된 유저가 없으면 예외가 발생한다`() {
            // when & then
            assertThatThrownBy {
                userService.getUser(0L)
            }
            .isInstanceOf(CoreException::class.java)
            .hasMessage(ErrorType.NOT_FOUND.message)
            .extracting("errorType").isEqualTo(ErrorType.NOT_FOUND)
        }
    }

}