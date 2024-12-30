package io.dimipay.server.module.user.domain.vo

import io.dimipay.server.common.util.Argon2
import io.dimipay.server.module.user.domain.exception.BiokeyException
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
    if (this.biokey == null) {
      throw BiokeyException()
    }
    return Argon2.matches(biokey, this.biokey!!)
  }
}
