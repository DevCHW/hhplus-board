package com.hhplus.board.utils

import com.example.ktboard.domain.error.CoreException
import com.example.ktboard.domain.error.ErrorType

fun notFound(): Nothing {
    throw CoreException(ErrorType.NOT_FOUND)
}