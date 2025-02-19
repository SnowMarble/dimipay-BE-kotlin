package io.dimipay.server.auth.application.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.intercept.AuthorizationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val accessDeniedHandler: AccessDeniedHandler,
    private val authenticationEntryPoint: AuthenticationEntryPoint,
    private val jwtService: JwtService
) {

  @Bean
  fun jwtAuthenticationProvider(): JwtAuthenticationProvider {
    return JwtAuthenticationProvider(jwtService)
  }

  @Bean
  fun authenticationManager(): AuthenticationManager {
    return ProviderManager(jwtAuthenticationProvider())
  }

  @Bean
  fun bearerTokenFilter(): BearerTokenFilter {
    return BearerTokenFilter(authenticationManager(), authenticationEntryPoint)
  }

  @Bean
  fun filterChain(http: HttpSecurity): SecurityFilterChain {
    http {
      csrf {
        disable()
      }
      authorizeHttpRequests {
        authorize(anyRequest, permitAll) // see https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html#request-vs-method
      }
      sessionManagement {
        sessionCreationPolicy = SessionCreationPolicy.STATELESS
      }
      exceptionHandling {
        accessDeniedHandler = this@SecurityConfig.accessDeniedHandler
        authenticationEntryPoint = this@SecurityConfig.authenticationEntryPoint
      }
      addFilterBefore<AuthorizationFilter>(bearerTokenFilter())
    }

    return http.build()
  }
}