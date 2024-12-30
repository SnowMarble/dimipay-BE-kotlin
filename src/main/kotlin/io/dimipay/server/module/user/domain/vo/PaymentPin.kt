package io.dimipay.server.module.user.domain.vo

import io.dimipay.server.common.util.Argon2
import io.dimipay.server.module.user.domain.exception.PaymentPinConsecutiveNumberException
import io.dimipay.server.module.user.domain.exception.PaymentPinFormatException
import io.dimipay.server.module.user.domain.exception.PaymentPinNotSetException
import io.dimipay.server.module.user.domain.exception.PaymentPinSameNumberException
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import lombok.NoArgsConstructor

/**
 * - 4 자리 숫자로 이루어짐.
 * - 같은 숫자로 이루어지면 안됨
 * - 연속된 숫자로 이루어지면 안됨.
 */
@Embeddable
@NoArgsConstructor
data class PaymentPin private constructor(
    @Column(name = "payment_pin", nullable = true)
    val paymentPin: String?
) {
  companion object {
    fun create(raw: String): PaymentPin {
      checkFormat(raw)
      checkIsSame(raw)
      checkIsConsecutive(raw)
      return PaymentPin(Argon2.encode(raw))
    }

    private fun checkFormat(raw: String) {
      if (!Regex("^\\d{4}$").matches(raw)) {
        throw PaymentPinFormatException()
      }
    }

    private fun checkIsSame(raw: String) {
      if (raw.toSet().size == 1) {
        throw PaymentPinSameNumberException()
      }
    }

    private fun checkIsConsecutive(raw: String) {
      val diff = raw[1].digitToInt() - raw[0].digitToInt()

      if (diff != -1 && diff != 1) {
        return
      }

      val isConsecutive = raw.windowed(2).all { pair ->
        pair[1].digitToInt() - pair[0].digitToInt() == diff
      }

      if (isConsecutive) {
        throw PaymentPinConsecutiveNumberException()
      }
    }
  }

  fun matches(raw: String): Boolean {
    if (this.paymentPin == null) {
      throw PaymentPinNotSetException()
    }
    return Argon2.matches(raw, this.paymentPin!!)
  }
}