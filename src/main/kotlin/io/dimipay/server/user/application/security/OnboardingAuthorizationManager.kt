package io.dimipay.server.user.application.security

import io.dimipay.server.auth.application.security.JwtAuthenticationToken
import io.dimipay.server.user.application.security.annotation.AllowOnboarding
import org.aopalliance.intercept.MethodInvocation
import org.slf4j.LoggerFactory
import org.springframework.security.authorization.AuthorizationDecision
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.authorization.AuthorizationResult
import org.springframework.security.core.Authentication
import java.util.function.Supplier

class OnboardingAuthorizationManager : AuthorizationManager<MethodInvocation> {

  private val logger = LoggerFactory.getLogger(OnboardingAuthorizationManager::class.java)

  override fun authorize(authentication: Supplier<Authentication>, method: MethodInvocation): AuthorizationResult {
    println("onboardingAuthorizationManager")
    if (shouldSkip(method)) {
      logger.debug("Skip onboarding authorization for method: {}", method.method.name)
      return AuthorizationDecision(true)
    }

    val claims = (authentication.get() as JwtAuthenticationToken).claims!!

    if (claims["onboarding"] != true) {
      throw OnboardingRequiredException()
    }

    return AuthorizationDecision(true)
  }

  private fun shouldSkip(method: MethodInvocation): Boolean {
    return method.method.getAnnotation(AllowOnboarding::class.java) != null
  }

  @Deprecated("Deprecated in AuthenticationManager", ReplaceWith("authorize(authentication, method)"))
  override fun check(authentication: Supplier<Authentication>, method: MethodInvocation): AuthorizationDecision? {
    return null
  }
}