package io.dimipay.server.auth.application.security

import com.fasterxml.jackson.annotation.JsonValue

interface JwtClaims {

  val sub: String

  val type: JwtType

  val client: ClientType

  fun toMap(): Map<String, Any> = buildMap {
    put("sub", sub)
    put("type", type.value)
    put("client", client.value)
  }
}

enum class JwtType(@JsonValue val value: String) {
  ACCESS("access"),
  REFRESH("refresh"),
  PAYMENT_PIN_AUTH("pin"),
}

enum class ClientType(@JsonValue val value: String) {
  USER("user"),
  KIOSK("kiosk"),
  ADMIN("admin"),
}
