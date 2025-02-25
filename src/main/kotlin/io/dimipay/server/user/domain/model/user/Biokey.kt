package io.dimipay.server.user.domain.model.user

import io.dimipay.server.common.util.security.Argon2
import io.dimipay.server.user.exception.BiokeyNotSetException
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
@ConsistentCopyVisibility
data class Biokey private constructor(
    @Column(name = "biokey", nullable = true)
    var biokey: String?
) {

  companion object {

    fun create(biokey: String): Biokey {
      return Biokey(Argon2.encode(biokey))
    }
  }

  fun matches(biokey: String): Boolean {
    checkNotNull(this.biokey) { throw BiokeyNotSetException() }
    return Argon2.matches(biokey, this.biokey!!)
  }
}
