package io.dimipay.server.user.domain.model.user

import io.dimipay.server.common.util.security.Argon2
import io.dimipay.server.user.exception.DeviceIdException
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
@ConsistentCopyVisibility
data class DeviceId private constructor(
    @Column(name = "device_id", nullable = true)
    var deviceId: String?
) {

  companion object {

    fun create(deviceId: String): DeviceId {
      return DeviceId(Argon2.encode(deviceId))
    }
  }

  fun matches(deviceId: String): Boolean {
    if (this.deviceId == null) {
      throw DeviceIdException()
    }
    return Argon2.matches(deviceId, this.deviceId!!)
  }
}