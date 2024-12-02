package com.hhplus.board.api.global.argumentresolver

import com.example.ktboard.domain.error.CoreException
import com.example.ktboard.domain.error.ErrorType
import com.hhplus.board.api.global.jwt.CustomUserDetails
import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

class LoginHandlerMethodArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.getParameterAnnotation(Login::class.java) != null &&
                parameter.parameterType == LoginUser::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val authentication = SecurityContextHolder.getContext().authentication

        if (authentication == null || !authentication.isAuthenticated) {
            throw CoreException(ErrorType.ACCESS_DENIED)
        }

        if (authentication.principal is CustomUserDetails) {
            val userDetails = authentication.principal as CustomUserDetails
            return LoginUser(
                id = userDetails.id
            )
        }
        throw CoreException(ErrorType.DEFAULT_SERVER_ERROR)
    }
}