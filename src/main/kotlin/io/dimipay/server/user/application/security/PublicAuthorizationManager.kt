package io.dimipay.server.user.application.security

import io.dimipay.server.auth.application.annotation.Public
import org.aopalliance.intercept.MethodInvocation
import org.springframework.security.authorization.AuthorizationDecision
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.authorization.AuthorizationResult
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.function.Supplier

@Component
class PublicAuthorizationManager : AuthorizationManager<MethodInvocation> {

  override fun authorize(authentication: Supplier<Authentication>, method: MethodInvocation): AuthorizationResult? {
    val isPublic = method.method.getAnnotation(Public::class.java) != null

    if (isPublic) {
      return AuthorizationDecision(true)
    }

    return null
  }

  @Deprecated("Deprecated", ReplaceWith("authorize(authentication, result)"))
  override fun check(authentication: Supplier<Authentication>, method: MethodInvocation): AuthorizationDecision? {
    return null
  }
}