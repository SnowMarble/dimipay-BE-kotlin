package io.dimipay.server.module.user.domain.vo

import io.dimipay.server.module.user.exception.UserNameException
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertTrue

class CurrentUserNameTest {

  @Test
  fun testConstructor() {
    val userName = UserName("이름")

    assert(userName.name == "이름")
  }

  @Test
  fun testInvalidCharactersIncluded() {
    assertThrows<UserNameException> {
      UserName("sahbsaihuafsiaislfhsiauf")
    }
  }

  @Test
  fun testDefaultName() {
    val userName = UserName.createSanitized("\uD83D\uDE06")

    assertTrue("^사용자\\d{4}$".toRegex().matches(userName.name))
  }
}