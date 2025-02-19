package io.dimipay.server.auth.application.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.web.filter.OncePerRequestFilter

class BearerTokenFilter(
    private val authenticationManager: AuthenticationManager,
    private val authenticationEntryPoint: AuthenticationEntryPoint
) : OncePerRequestFilter() {

  private val securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy()

  override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
    val token = getBearerToken(request)

    if (token == null) {
      logger.debug("Skip bearer token filter for request: ${request.requestURI}")
      filterChain.doFilter(request, response)
      return
    }

    val authenticationToken = JwtAuthenticationToken(token)

    try {
      val authenticationResult = authenticationManager.authenticate(authenticationToken)

      val context = SecurityContextHolder.createEmptyContext()
      context.authentication = authenticationResult
      securityContextHolderStrategy.context = context

      filterChain.doFilter(request, response)
    } catch (ex: AuthenticationException) {
      logger.debug("Failed to authenticate bearer token", ex)
      securityContextHolderStrategy.clearContext()
      authenticationEntryPoint.commence(request, response, ex)
    }
  }

  private fun getBearerToken(request: HttpServletRequest): String? {
    return request.getHeader("Authorization")
        ?.takeIf { it.startsWith("Bearer ") }
        ?.substring(7)
  }
}