package com.hhplus.board.utils

import com.example.ktboard.domain.error.CoreException
import com.example.ktboard.domain.error.ErrorType

fun notFountThrow(): Nothing {
    throw CoreException(ErrorType.NOT_FOUND)
}