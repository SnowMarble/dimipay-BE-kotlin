package io.dimipay.server.auth.application.security

import io.dimipay.server.user.presentation.controller.UserEndpoints
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.annotation.AnnotationTemplateExpressionDefaults
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
  fun templateExpressionDefaults(): AnnotationTemplateExpressionDefaults {
    return AnnotationTemplateExpressionDefaults()
  }

  @Bean
  fun filterChain(http: HttpSecurity): SecurityFilterChain {
    http {
      csrf {
        disable()
      }
      authorizeHttpRequests {
        authorize(UserEndpoints.OAUTH2_LOGIN, permitAll)
        authorize(UserEndpoints.OAUTH2_CODE_GOOGLE, permitAll)
        authorize(UserEndpoints.OAUTH2_AUTHORIZE_GOOGLE, permitAll)
        authorize(UserEndpoints.REFRESH_TOKEN, permitAll)
        authorize(anyRequest, authenticated)
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