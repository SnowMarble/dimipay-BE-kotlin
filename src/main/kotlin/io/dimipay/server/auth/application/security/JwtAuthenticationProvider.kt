package io.dimipay.server.auth.application.security

import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

class JwtAuthenticationProvider(
    private val jwtService: JwtService
) : AuthenticationProvider {

  private val logger = LoggerFactory.getLogger(JwtAuthenticationProvider::class.java)

  override fun supports(authentication: Class<*>): Boolean {
    return JwtAuthenticationToken::class.java.isAssignableFrom(authentication)
  }

  override fun authenticate(authentication: Authentication): Authentication? {
    val authenticationRequest = authentication as JwtAuthenticationToken
    val claims = jwtService.verify(authenticationRequest.credentials)

    if (claims["type"] != JwtType.ACCESS.value) {
      logger.debug("jwt type is not access: {}", claims["type"])
      return null
    }

    val authorities = mutableListOf<GrantedAuthority>()

    val client = (claims["client"] as String).uppercase()
    val role = SimpleGrantedAuthority("ROLE_$client")
    authorities.add(role)

    val authenticationToken = JwtAuthenticationToken(
        authenticationRequest.credentials,
        (claims["sub"] as String).toLong(),
        authorities,
        claims
    )

    return authenticationToken
  }
}