package io.dimipay.server.common.util.security

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder

class Argon2 {
  companion object {

    private val encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8()

    fun encode(raw: String): String {
      return encoder.encode(raw)
    }

    fun matches(raw: String, encoded: String): Boolean {
      return encoder.matches(raw, encoded)
    }
  }
}