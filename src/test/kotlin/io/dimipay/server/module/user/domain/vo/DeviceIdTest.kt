package io.dimipay.server.module.user.domain.vo

import io.dimipay.server.module.user.exception.DeviceIdException
import io.dimipay.server.user.domain.model.user.DeviceId
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNull

class DeviceIdTest {

  @Test
  fun testConstructor() {
    val deviceId = DeviceId("e34ff064-7f79-44db-ae44-6422714ab616")

    assertIs<String>(deviceId.toString())
  }

  @Test
  fun testConstructorWithNull() {
    val deviceId = DeviceId(null)

    assertNull(deviceId.deviceId)
  }

  @Test
  fun testMatches() {
    val deviceId = DeviceId("e34ff064-7f79-44db-ae44-6422714ab616")

    assertTrue(deviceId.matches("e34ff064-7f79-44db-ae44-6422714ab616"))
  }

  @Test
  fun testNotMatches() {
    val deviceId = DeviceId("e34ff064-7f79-44db-ae44-6422714ab616")

    assertFalse(deviceId.matches("5cdb26e2-c284-4133-b287-bdc6fcd76a38"))
  }

  @Test
  fun testMatchesWithNull() {
    val deviceId = DeviceId(null)

    assertThrows<DeviceIdException> {
      deviceId.matches("e34ff064-7f79-44db-ae44-6422714ab616")
    }
  }
}