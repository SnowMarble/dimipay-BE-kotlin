package io.dimipay.server.user.application.security

import io.dimipay.server.auth.application.security.ClientType
import io.dimipay.server.auth.application.security.JwtClaims
import io.dimipay.server.auth.application.security.JwtType

class UserRefreshTokenClaims(
    override val sub: String,
) : JwtClaims {

  override val type: JwtType = JwtType.REFRESH

  override val client: ClientType = ClientType.USER
}