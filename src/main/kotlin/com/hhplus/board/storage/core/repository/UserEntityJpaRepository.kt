package com.hhplus.board.storage.core.repository

import com.hhplus.board.storage.core.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserEntityJpaRepository : JpaRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity?
}