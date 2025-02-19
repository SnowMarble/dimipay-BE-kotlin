package io.dimipay.server.auth.application.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

/**
 * ## Note
 * - principal: identifier of user like id, and email. In our case, it's user id.
 * - credentials: password or token. In our case, it's access token.
 * - authorities: roles or permissions granted to user.
 * - details: additional information about user like ip address, device id, etc.
 * - isAuthenticated flag: verifying token does not mean user is authenticated. It does when auithentication provider is successful.
 * - claims: custom field for jwt claims
 */
open class JwtAuthenticationToken : AbstractAuthenticationToken {

  private val token: String
  private val principal: Long?
  val claims: Map<String, Any>?

  constructor(token: String) : super(emptyList()) {
    this.token = token
    this.principal = null
    this.claims = null
    this.isAuthenticated = false
  }

  constructor(
      token: String,
      principal: Long,
      authorities: Collection<GrantedAuthority>,
      claims: Map<String, Any>
  ) : super(authorities) {
    this.token = token
    this.principal = principal
    this.claims = claims
    this.isAuthenticated = true
  }

  override fun getCredentials(): String = token

  override fun getPrincipal(): Long? = principal
}