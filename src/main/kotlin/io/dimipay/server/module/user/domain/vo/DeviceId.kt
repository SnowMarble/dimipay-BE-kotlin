package io.dimipay.server.module.user.domain.vo

import io.dimipay.server.common.util.Argon2
import io.dimipay.server.module.user.domain.exception.DeviceIdException
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class DeviceId(
    @Column(name = "device_id", nullable = true)
    final var deviceId: String?
) {
  init {
    if (deviceId != null) {
      this.deviceId = Argon2.encode(deviceId!!)
    }
  }

  fun matches(deviceId: String): Boolean {
    if (this.deviceId == null) {
      throw DeviceIdException()
    }
    return Argon2.matches(deviceId, this.deviceId!!)
  }
}