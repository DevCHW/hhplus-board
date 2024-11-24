package com.example.ktboard.domain.error

import com.example.ktboard.domain.error.ErrorCode.*
import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*

enum class ErrorType(
    val status: HttpStatus,
    val code: ErrorCode,
    val message: String,
    val logLevel: LogLevel,
) {
    DEFAULT_SERVER_ERROR(INTERNAL_SERVER_ERROR, ERROR_001, "An unexpected error has occurred.", LogLevel.ERROR),
    TOKEN_INVALID(UNAUTHORIZED, ERROR_002, UNAUTHORIZED.reasonPhrase, LogLevel.WARN),
    TOKEN_EXPIRED(UNAUTHORIZED, ERROR_002, UNAUTHORIZED.reasonPhrase, LogLevel.WARN),
    ACCESS_DENIED(FORBIDDEN, ERROR_003, FORBIDDEN.reasonPhrase, LogLevel.WARN),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, ERROR_004, HttpStatus.BAD_REQUEST.reasonPhrase, LogLevel.WARN),
    NOT_FOUND(HttpStatus.NOT_FOUND, ERROR_005, HttpStatus.NOT_FOUND.reasonPhrase, LogLevel.WARN),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, ERROR_006, HttpStatus.BAD_REQUEST.reasonPhrase, LogLevel.WARN),
    SERVER_UNPROCESSABLE(UNPROCESSABLE_ENTITY, ERROR_007, SERVICE_UNAVAILABLE.reasonPhrase, LogLevel.WARN),
}