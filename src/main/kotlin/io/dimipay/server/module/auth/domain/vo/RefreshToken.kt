package io.dimipay.server.module.auth.domain.vo

import io.dimipay.server.common.util.Argon2
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import lombok.NoArgsConstructor

@Embeddable
@NoArgsConstructor
data class RefreshToken private constructor(
    @Column(name = "refresh_token", nullable = true)
    val refreshToken: String?
) {
  companion object {
    fun create(raw: String): RefreshToken {
      return RefreshToken(Argon2.encode(raw))
    }
  }

  fun matches(raw: String): Boolean {
    if (this.refreshToken == null) {
      throw IllegalStateException("RefreshToken is not set")
    }
    return Argon2.matches(raw, this.refreshToken!!)
  }
}