package io.dimipay.server.auth.domain.vo

import io.dimipay.server.common.util.security.Argon2
import jakarta.persistence.Basic
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.FetchType

@Embeddable
data class RefreshToken(
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "refresh_token", nullable = true)
    val refreshToken: String?
) {

  companion object {

    fun create(raw: String): RefreshToken {
      return RefreshToken(Argon2.encode(raw))
    }
  }

  fun matches(raw: String): Boolean {
    checkNotNull(this.refreshToken) { "RefreshToken is not set" }
    return Argon2.matches(raw, this.refreshToken!!)
  }
}