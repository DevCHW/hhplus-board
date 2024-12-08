package com.hhplus.board.storage.core.user

import com.hhplus.board.storage.core.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class UserEntity(
    @Column(name = "username", unique = true)
    val username: String,

    @Column(name = "password")
    val password: String,
) : BaseEntity()