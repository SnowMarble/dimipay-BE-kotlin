package io.dimipay.server.user.domain.model.user

import io.dimipay.server.user.exception.PaymentPinException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class UserTest {

  private lateinit var user: User

  @BeforeEach
  fun setUp() {
    val name = UserName("test")
    user = User.create(name, "test@dimipay.com", "1234", "1234", "dimigo.hs.kr")
  }

  @Test
  fun `should throw when user tries to regiter a paymnt pin when it's already set`() {
    user.setPaymentPin("1212")

    assertThrows<PaymentPinException.PaymentPinAlreadySetException> {
      user.setPaymentPin("1221")
    }
  }

  @Test
  fun `should throw when user tries to update a paymnt pin when it's not set`() {
    assertThrows<PaymentPinException.PaymentPinNotSetException> {
      user.updatePaymentPin("1221")
    }
  }

  @Test
  fun `should throw when payment pin verification failed`() {
    user.setPaymentPin("1212")

    assertThrows<PaymentPinException.InvalidPaymentPinException> {
      user.verifyPaymentPin("1221")
    }
    assert(user.pinTryCount == 1)
  }

  @Test
  fun `should throw when payment pin try count exceeded`() {
    user.setPaymentPin("1212")
    user.pinTryCount = 4

    assertThrows<PaymentPinException.InvalidPaymentPinException> {
      user.verifyPaymentPin("1221")
    }
    assertThrows<PaymentPinException.PaymentPinTryCountLimitException> {
      user.verifyPaymentPin("1221")
    }
    assert(user.pinTryCount == 5)
  }

  @Test
  fun `should reset payment pin try count when payment pin verification success`() {
    user.setPaymentPin("1212")
    user.pinTryCount = 2

    user.verifyPaymentPin("1212")

    assert(user.pinTryCount == 0)
  }
}