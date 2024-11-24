package com.example.ktboard.domain.error

class CoreException(
    val errorType: ErrorType,
    val errorMessage: String? = null,
    val data: Any? = null,
) : RuntimeException(errorMessage?: errorType.message)
