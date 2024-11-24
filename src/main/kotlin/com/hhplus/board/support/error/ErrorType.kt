package com.example.ktboard.domain.error

import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*

enum class ErrorType(
    val status: HttpStatus,
    val code: ErrorCode,
    val message: String,
    val logLevel: LogLevel,
) {

    TOKEN_INVALID(UNAUTHORIZED, ErrorCode.CODE003, "Unauthorized", LogLevel.WARN),
    TOKEN_EXPIRED(UNAUTHORIZED, ErrorCode.CODE003, "Unauthorized", LogLevel.WARN),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, ErrorCode.CODE002, "Bad Request", LogLevel.WARN),
    DEFAULT_SERVER_ERROR(INTERNAL_SERVER_ERROR, ErrorCode.CODE001, "An unexpected error has occurred.", LogLevel.ERROR),
}