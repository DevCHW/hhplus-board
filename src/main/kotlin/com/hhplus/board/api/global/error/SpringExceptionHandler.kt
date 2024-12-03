package com.hhplus.board.api.global.error

import com.example.ktboard.domain.error.ErrorType
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.hhplus.board.support.response.ApiResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.ConstraintViolationException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
@Order(0)
class SpringExceptionHandler {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    // Request Body 검증을 통과하지 못한 경우 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun handleMethodArgumentNotValid(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        e: MethodArgumentNotValidException
    ): ResponseEntity<ApiResponse<Any>> {
        val bindingResult = e.bindingResult
        val logMessage = StringBuilder()
        val responseMessage = StringBuilder()

        if (!bindingResult.fieldErrors.isEmpty()) {
            val fieldError = bindingResult.fieldErrors[0]
            logMessage
                .append("Validation fail field : [")
                .append(fieldError.field).append("] / Message : [")
                .append(fieldError.defaultMessage).append("]")
            responseMessage.append(fieldError.defaultMessage)
        }

        val errorType: ErrorType = ErrorType.VALIDATION_ERROR
        log.warn(logMessage.toString())

        return ResponseEntity(ApiResponse.error(errorType, responseMessage), errorType.status)
    }

    // 필수 값인 RequestParam 이 없는 경우 예외 처리
    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingServletRequestParameter(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        e: MissingServletRequestParameterException
    ): ResponseEntity<ApiResponse<Any>> {
        val errorType: ErrorType = ErrorType.BAD_REQUEST
        val logMessage = "Request parameter is missing. parameter name = " + e.parameterName
        log.warn(logMessage)

        return ResponseEntity(ApiResponse.error(errorType), errorType.status)
    }

    // PathVariable, RequestParam 의 타입이 맞지 않을 경우 예외 처리
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatch(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        e: MethodArgumentTypeMismatchException
    ): ResponseEntity<ApiResponse<Any>> {
        val errorType: ErrorType = ErrorType.BAD_REQUEST
        val parameter = e.parameter
        val logMessage = "Failed to convert value of type. parameter name = " + parameter.parameterName
        log.warn(logMessage)

        return ResponseEntity(ApiResponse.error(errorType), errorType.status)
    }

    // PathVariable, RequestParam 의 타입이 맞지 않을 경우 예외 처리
    @ExceptionHandler(ConstraintViolationException::class)
    protected fun handlerConstraintViolation(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        e: ConstraintViolationException?
    ): ResponseEntity<ApiResponse<Any>> {
        val errorType: ErrorType = ErrorType.BAD_REQUEST
        log.warn(e?.message ?: errorType.message)
        return ResponseEntity(ApiResponse.error(errorType), errorType.status)
    }

    // Request Body의 타입이 맞지 않거나, DTO에 기본생성자가 없는 경우 예외 처리
    @ExceptionHandler(HttpMessageNotReadableException::class)
    protected fun handlerHttpMessageNotReadable(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        e: HttpMessageNotReadableException
    ): ResponseEntity<ApiResponse<Any>> {
        // DTO에 기본 생성자가 없는 경우 (서버 잘못)
        if (e.message != null && e.message!!.contains("Cannot construct")) {
            val errorType: ErrorType = ErrorType.DEFAULT_SERVER_ERROR
            log.error(e.message ?: errorType.message)
            return ResponseEntity(ApiResponse.error(errorType), errorType.status)
        }

        // Request Body 필드의 Type이 맞지 않을 경우 (클라이언트 잘못)
        if (e.cause is MismatchedInputException) {
            val errorType: ErrorType = ErrorType.VALIDATION_ERROR

            // 타입이 맞지 않는 필드 이름 가져오기
            val mismatchedInputException = e.cause as MismatchedInputException?
            val filedNameBuilder = StringBuilder()
            val len = mismatchedInputException!!.path.size
            for (i in 0 until len) {
                val fieldName = mismatchedInputException.path[i].fieldName ?: continue

                filedNameBuilder.append(fieldName)
                if (i + 1 != len) filedNameBuilder.append(".")
            }
            val fieldName = filedNameBuilder.toString()
            val message = "$fieldName is type mismatched."
            log.warn(message)
            return ResponseEntity(ApiResponse.error(errorType, message), errorType.status)
        }

        // 서버 잘못
        val errorType: ErrorType = ErrorType.DEFAULT_SERVER_ERROR
        log.error(e.message ?: errorType.message)
        return ResponseEntity(ApiResponse.error(errorType), errorType.status)
    }

    // 지원하지 않는 HTTP 메소드 호출 예외에 대한 처리
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupported(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        e: HttpRequestMethodNotSupportedException
    ): ResponseEntity<ApiResponse<Any>> {
        val errorType: ErrorType = ErrorType.BAD_REQUEST

        log.warn(e.message ?: errorType.message)
        return ResponseEntity(ApiResponse.error(errorType), errorType.status)
    }

    // 리소스를 찾을 수 없는 경우의 예외 처리
    @ExceptionHandler(NoResourceFoundException::class)
    fun handleNoResourceFound(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        e: NoResourceFoundException?
    ): ResponseEntity<ApiResponse<Any>> {
        val errorType: ErrorType = ErrorType.NOT_FOUND

        log.warn(e?.message ?: errorType.message)
        return ResponseEntity(ApiResponse.error(errorType), errorType.status)
    }
}