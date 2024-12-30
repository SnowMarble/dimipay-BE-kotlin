package io.dimipay.server.common.util

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class NameTest {
  @ParameterizedTest
  @ValueSource(
      strings = [
        "이름",
        "name",
        "가나다라마바사아자차",
        "365",
        "195081",
        "_)(_@%*!&",
      ]
  )
  fun testRegularNameMatches(name: String) {
    assertTrue(Name.regularName.matches(name))
  }


  @ParameterizedTest
  @ValueSource(
      strings = [
        "afnajkngkljnasdgkljngsd",
        "😗😗😗😗😗😗",
        "며ㅑㅁ러ㅏ91ㅜㅋㅁ노하",
        "┼",
        "㎚",
        "ㅭㅋㆅ",
        "\u1680\u2002\u3000", // 공백 문자 몇 개
      ]
  )
  fun testToRegularNameNotMatches(name: String) {
    assertFalse(Name.regularName.matches(name))
  }

  @ParameterizedTest
  @CsvSource(
      "가나다라마바사아자차카, 가나다라마바사아자차",
      "이름, 이름",
      "1234567890123456, 1234567890",
      "😗😗😗😗😗😗, default name",
      " 1234567890, 1234567890",
  )
  fun testToRegularName(name: String, expected: String) {
    assertEquals(Name.toRegularName(name) { "default name" }, expected)
  }
}
