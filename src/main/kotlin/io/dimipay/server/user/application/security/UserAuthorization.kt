package io.dimipay.server.user.application.security

import io.dimipay.server.auth.application.security.JwtAuthenticationToken
import io.dimipay.server.common.security.RoleNotFoundException
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations
import org.springframework.stereotype.Component

@Component("userAuthorization")
class UserAuthorization {

  fun authorize(operations: MethodSecurityExpressionOperations, allowOnboarding: Boolean): Boolean {
    if (!operations.hasRole("USER")) {
      throw RoleNotFoundException("USER")
    }

    if (!allowOnboarding && (operations.authentication as JwtAuthenticationToken).claims?.get("onboarding") == true) {
      throw OnboardingRequiredException()
    }

    return true
  }
}