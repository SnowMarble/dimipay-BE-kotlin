package io.dimipay.server.user.application.security

import io.dimipay.server.auth.application.security.ClientType
import io.dimipay.server.auth.application.security.JwtClaims
import io.dimipay.server.auth.application.security.JwtType

data class PaymentPinJwtClaims(
    override val sub: String,
) : JwtClaims {

  override val type = JwtType.PAYMENT_PIN_AUTH

  override val client = ClientType.USER
}