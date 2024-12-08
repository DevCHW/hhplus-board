package com.hhplus.board.storage.core.board

import org.springframework.data.jpa.repository.JpaRepository

interface BoardEntityJpaRepository : JpaRepository<BoardEntity, Long> {
}