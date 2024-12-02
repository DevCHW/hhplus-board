package com.hhplus.board.api.v1.user.application

import com.hhplus.board.api.v1.user.application.dto.spec.SignUpSpec
import com.hhplus.board.api.v1.user.domain.UserService
import org.assertj.core.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import kotlin.test.Test

@SpringBootTest
@Transactional
class UserFacadeTest(
    @Autowired val userFacade: UserFacade,
    @Autowired val userService: UserService,
) {

    @Test
    fun `회원가입 스펙을 통해 회원가입을 할 수 있다`() {
        // given
        val signUpSpec = SignUpSpec.fixture()

        // when
        val result = userFacade.signUp(signUpSpec)
        val user = userService.getUser(result.id)

        // then
        assertThat(result.id).isEqualTo(user.id)
        assertThat(result.username).isEqualTo(user.username)
        assertThat(result.createdAt).isEqualTo(user.createdAt)
        assertThat(result.updatedAt).isEqualTo(user.updatedAt)
    }

}