package com.hhplus.board.storage.core.repository

import com.hhplus.board.storage.core.entity.BoardEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BoardEntityJpaRepository : JpaRepository<BoardEntity, Long> {
}