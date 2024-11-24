package com.example.ktboard.domain.error

class CoreException(
    val errorType: ErrorType,
    val data: Any? = null,
    errorMessage: String? = null
) : RuntimeException(errorMessage?: errorType.message)
