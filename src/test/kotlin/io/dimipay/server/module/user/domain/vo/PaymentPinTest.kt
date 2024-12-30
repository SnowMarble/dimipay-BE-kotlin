package io.dimipay.server.module.user.domain.vo

import io.dimipay.server.module.user.domain.exception.PaymentPinConsecutiveNumberException
import io.dimipay.server.module.user.domain.exception.PaymentPinFormatException
import io.dimipay.server.module.user.domain.exception.PaymentPinSameNumberException
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertIs

class PaymentPinTest {

  @ParameterizedTest
  @ValueSource(
      strings = ["1212", "2518", "8581", "8516", "1204", "6100"]
  )
  fun `create payment pin`(input: String) {
    val paymentPin = PaymentPin.create(input)

    assertIs<String>(paymentPin.toString())
  }


  @ParameterizedTest
  @ValueSource(
      strings = ["1111", "4444", "9999", "0000"]
  )
  fun `throw when payment pin consists of same number`(input: String) {
    assertThrows<PaymentPinSameNumberException> {
      PaymentPin.create(input)
    }
  }

  @ParameterizedTest
  @ValueSource(
      strings = ["1-2-", "174817", "8591757192", "123", "", "1"]
  )
  fun `throw when payment pin is not 4 digits number`(input: String) {
    assertThrows<PaymentPinFormatException> {
      PaymentPin.create(input)
    }
  }

  @ParameterizedTest
  @ValueSource(
      strings = ["1234", "4321", "4567", "9876", "0123", "8765"]
  )
  fun `throw when payment pin is consecutive number`(input: String) {
    assertThrows<PaymentPinConsecutiveNumberException> {
      PaymentPin.create(input)
    }
  }
}