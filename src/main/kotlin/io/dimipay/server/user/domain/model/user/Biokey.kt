package io.dimipay.server.user.domain.model.user

import io.dimipay.server.common.util.security.Argon2
import io.dimipay.server.user.exception.BiokeyNotSetException
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Biokey(
    @Column(name = "biokey", nullable = true)
    final var biokey: String?
) {

  init {
    if (biokey != null) {
      this.biokey = Argon2.encode(biokey!!)
    }
  }

  fun matches(biokey: String): Boolean {
    checkNotNull(this.biokey) { throw BiokeyNotSetException() }
    return Argon2.matches(biokey, this.biokey!!)
  }
}
