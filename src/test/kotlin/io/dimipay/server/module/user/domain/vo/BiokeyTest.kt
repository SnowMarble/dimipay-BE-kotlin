package io.dimipay.server.module.user.domain.vo

import io.dimipay.server.module.user.exception.BiokeyNotSetException
import org.junit.jupiter.api.assertThrows
import kotlin.test.*

class BiokeyTest {

  @Test
  fun testConstructor() {
    val biokey = Biokey("e34ff064-7f79-44db-ae44-6422714ab616")

    assertIs<String>(biokey.toString())
  }

  @Test
  fun testConstructorWithNull() {
    val biokey = Biokey(null)

    assertNull(biokey.biokey)
  }

  @Test
  fun testMatches() {
    val biokey = Biokey("e34ff064-7f79-44db-ae44-6422714ab616")

    assertTrue(biokey.matches("e34ff064-7f79-44db-ae44-6422714ab616"))
  }

  @Test
  fun testNotMatches() {
    val biokey = Biokey("e34ff064-7f79-44db-ae44-6422714ab616")

    assertFalse(biokey.matches("5cdb26e2-c284-4133-b287-bdc6fcd76a38"))
  }

  @Test
  fun testMatchesWithNull() {
    val biokey = Biokey(null)

    assertThrows<BiokeyNotSetException> {
      biokey.matches("e34ff064-7f79-44db-ae44-6422714ab616")
    }
  }
}