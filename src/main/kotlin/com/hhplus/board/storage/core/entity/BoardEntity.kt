package com.hhplus.board.storage.core.entity

import com.hhplus.board.storage.core.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "board")
class BoardEntity(
    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "username")
    var username: String,

    @Column(name = "title")
    var title: String,

    @Column(name = "content")
    var content: String,

    @Column(name = "password")
    val password: String,
) : BaseEntity() {

    fun modify(modifyEntity: ModifyBoardEntity) {
        this.username = modifyEntity.username
        this.content = modifyEntity.content
        this.title = modifyEntity.title
    }

    data class ModifyBoardEntity(
        val username: String,
        val title: String,
        val content: String,
    )
}