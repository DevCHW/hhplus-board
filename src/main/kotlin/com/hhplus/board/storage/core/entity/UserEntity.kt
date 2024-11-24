package com.hhplus.board.storage.core.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class UserEntity(
    @Column(name = "username")
    val username: String,

    @Column(name = "password")
    val password: String,
) : BaseEntity() {
}