package io.dimipay.server.user.domain.model.user

import io.dimipay.server.common.util.security.Argon2
import io.dimipay.server.user.exception.PaymentPinException
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

/**
 * Payment pin requirements:
 * - 4 digits of numbers.
 * - Same number is not allowed.
 * - Consecutive numbers are not allowed.
 */
@Embeddable
@ConsistentCopyVisibility // took me some time to find this one: https://youtrack.jetbrains.com/issue/KT-11914/Confusing-data-class-copy-with-private-constructor
data class PaymentPin private constructor(

    @Column(name = "payment_pin", nullable = true)
    val paymentPin: String
) {

  companion object {

    fun create(raw: String): PaymentPin {
      checkFormat(raw)
      checkIsSame(raw)
      checkIsSequential(raw)

      return PaymentPin(Argon2.encode(raw))
    }

    private fun checkFormat(raw: String) {
      if (!Regex("^\\d{4}$").matches(raw)) {
        throw PaymentPinException.PaymentPinFormatException()
      }
    }

    private fun checkIsSame(raw: String) {
      if (raw.toSet().size == 1) {
        throw PaymentPinException.PaymentPinSameNumberException()
      }
    }

    private fun checkIsSequential(raw: String) {
      val diff = raw[1].digitToInt() - raw[0].digitToInt()

      if (diff != -1 && diff != 1) {
        return
      }

      val isConsecutive = raw.windowed(2).all { pair ->
        pair[1].digitToInt() - pair[0].digitToInt() == diff
      }

      if (isConsecutive) {
        throw PaymentPinException.PaymentPinConsecutiveNumberException()
      }
    }

    /**
     * This is a null object.
     * @link https://refactoring.guru/introduce-null-object
     */
    val NULL = object : PaymentPin("") {

      override fun verify(raw: String): Boolean {
        throw PaymentPinException.PaymentPinNotSetException()
      }
    }
  }

  fun verify(raw: String): Boolean {
    return Argon2.matches(raw, this.paymentPin)
  }

  fun isNullObject(): Boolean = this == NULL
}