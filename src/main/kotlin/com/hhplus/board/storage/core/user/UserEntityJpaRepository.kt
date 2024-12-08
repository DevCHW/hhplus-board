package com.hhplus.board.storage.core.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserEntityJpaRepository : JpaRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity?
}