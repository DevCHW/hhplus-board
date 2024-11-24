package com.hhplus.board.api.global.config

import com.hhplus.board.api.global.jwt.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.*
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val tokenAuthenticationFilter: JwtAuthenticationFilter,
) {
    private val WHITE_LIST: Array<String> = arrayOf("/health", "/docs", "/error", "/favicon.ico")

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http // 기본 설정
            .cors { corsConfigurationSource() } // cors 설정
            .csrf { it.disable() } // csrf 비활성화
            .formLogin { it.disable() } // 기본 login form 비활성화
            .httpBasic { it.disable() } // 기본 인증 로그인 비활성화
            .logout { it.disable() } // 기본 logout 비활성화
            .headers {
                it.frameOptions { it.disable() } // X-Frame-Options 비활성화
            }
            .sessionManagement({ it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }) // 세션 사용 X

            // request 인증, 인가 설정
            .authorizeHttpRequests { request ->
                request
//                    .requestMatchers(*WHITE_LIST).permitAll()
                    .anyRequest().permitAll()
            }

            .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val corsConfig = CorsConfiguration()
        corsConfig.allowedMethods = listOf("*")
        corsConfig.allowedHeaders = listOf("*")
        corsConfig.allowedOrigins = listOf("*")

        val corsConfigSource = UrlBasedCorsConfigurationSource()
        corsConfigSource.registerCorsConfiguration("/**", corsConfig)
        return corsConfigSource
    }
}