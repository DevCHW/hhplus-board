package com.hhplus.board.api.v1.user.domain

import com.example.ktboard.domain.error.CoreException
import com.example.ktboard.domain.error.ErrorType
import com.hhplus.board.api.v1.user.domain.model.User
import com.hhplus.board.api.v1.user.domain.model.UserCreate
import com.hhplus.board.storage.core.repository.UserEntityJpaRepository
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
    fun create(userCreate: UserCreate): User {
        val savedEntity = userEntityJpaRepository.save(userCreate.toEntity())
        return User.from(savedEntity)
    }

    fun getUserByUsername(username: String): User {
        val userEntity = userEntityJpaRepository.findByUsername(username)?: throw CoreException(ErrorType.NOT_FOUND)
        return User.from(userEntity)
    }

}