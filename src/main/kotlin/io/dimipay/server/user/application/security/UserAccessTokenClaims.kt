package io.dimipay.server.user.application.security

import io.dimipay.server.auth.application.security.ClientType
import io.dimipay.server.auth.application.security.JwtClaims
import io.dimipay.server.auth.application.security.JwtType

class UserAccessTokenClaims(
    override val sub: String,
    private val onboarding: Boolean,
) : JwtClaims {

  override val type = JwtType.ACCESS

  override val client = ClientType.USER

  override fun toMap(): Map<String, Any> = buildMap {
    put("sub", sub)
    put("type", type.value)
    put("client", client.value)
    put("onboarding", onboarding)
  }
}