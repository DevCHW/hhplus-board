package com.hhplus.board.api.v1.user.domain

import com.hhplus.board.api.v1.user.domain.model.User
import com.hhplus.board.api.v1.user.domain.model.CreateUser
import com.hhplus.board.storage.core.user.UserEntityJpaRepository
import com.hhplus.board.utils.notFountThrow
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userEntityJpaRepository: UserEntityJpaRepository
) {

    /**
     * 유저 생성
     */
    @Transactional
    fun createUser(userCreate: CreateUser): User {
        val savedEntity = userEntityJpaRepository.save(userCreate.toEntity())
        return User.from(savedEntity)
    }

    /**
     * 유저 이름으로 유저 조회
     */
    fun getUserByUsername(username: String): User {
        val userEntity = userEntityJpaRepository.findByUsername(username)?: notFountThrow()
        return User.from(userEntity)
    }

    /**
     * 유저 아이디로 유저 조회
     */
    fun getUser(userId: Long): User {
        val userEntity = userEntityJpaRepository.findByIdOrNull(userId) ?: notFountThrow()
        return User.from(userEntity)
    }

}