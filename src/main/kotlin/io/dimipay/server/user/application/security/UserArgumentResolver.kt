package io.dimipay.server.user.application.security

import io.dimipay.server.auth.application.security.JwtAuthenticationToken
import io.dimipay.server.common.exception.ResourceNotFoundException
import io.dimipay.server.user.domain.model.user.User
import io.dimipay.server.user.domain.repository.UserRepository
import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class UserArgumentResolver(
    private val userRepository: UserRepository
) : HandlerMethodArgumentResolver {

  override fun supportsParameter(parameter: MethodParameter): Boolean {
    return parameter.parameterType.isAssignableFrom(User::class.java)
  }

  override fun resolveArgument(
      parameter: MethodParameter,
      mavContainer: ModelAndViewContainer?,
      webRequest: NativeWebRequest,
      binderFactory: WebDataBinderFactory?
  ): User? {
    val authentication = SecurityContextHolder.getContext().authentication as JwtAuthenticationToken

    if (!authentication.isAuthenticated) {
      return null
    }

    val userId = authentication.principal!!

    return userRepository.findById(userId).orElseThrow { throw ResourceNotFoundException() }
  }
}