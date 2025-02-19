package io.dimipay.server.user.application.security

import org.springframework.aop.Advisor
import org.springframework.aop.aspectj.AspectJExpressionPointcut
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Role
import org.springframework.security.authorization.AuthorityAuthorizationManager
import org.springframework.security.authorization.AuthorizationManagers
import org.springframework.security.authorization.method.AuthorizationManagerBeforeMethodInterceptor

@Configuration
class UserSecurityConfig {

  companion object {

    private const val USER_CONTROLLER_METHOD_EXPRESSION = "execution(* io.dimipay.server.user.presentation.controller.*.*(..))"

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @JvmStatic
    fun userAuthorizePointct(): Advisor {
      val pattern = AspectJExpressionPointcut()
      pattern.expression = USER_CONTROLLER_METHOD_EXPRESSION

      val authorizationManagers = AuthorizationManagers.anyOf(
          PublicAuthorizationManager(),
          AuthorizationManagers.allOf(
              AuthorityAuthorizationManager.hasRole("USER"),
              OnboardingAuthorizationManager(),
          )
      )

      return AuthorizationManagerBeforeMethodInterceptor(pattern, authorizationManagers)
    }
  }
}
