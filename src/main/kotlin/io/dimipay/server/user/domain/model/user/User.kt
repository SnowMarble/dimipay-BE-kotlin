package io.dimipay.server.user.domain.model.user

import io.dimipay.server.auth.domain.vo.RefreshToken
import io.dimipay.server.common.domain.BaseEntity
import io.dimipay.server.user.exception.OrganizationDomainNotAllowedException
import io.dimipay.server.user.exception.PaymentPinException
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class User private constructor(
    @Embedded
    val name: UserName,

    @Column(nullable = false)
    val email: String,

    @Column(name = "profile_image", nullable = false)
    val profileImage: String,

    @Column(nullable = false)
    val disabled: Boolean = false,

    @Embedded
    var biokey: Biokey? = null,

    @Embedded
    var deviceId: DeviceId? = null,

    @Embedded
    private var paymentPin: PaymentPin = PaymentPin.NULL,

    @Column(name = "pin_try_count")
    var pinTryCount: Int = 0,

    @Embedded
    var refreshToken: RefreshToken? = null,

    @Column(name = "google_id")
    val googleId: String,
) : BaseEntity() {

  companion object {

    private const val ORGANIZATION_DOMAIN = "dimigo.hs.kr"

    private const val PIN_TRY_COUNT_LIMIT = 5

    fun create(name: UserName, email: String, profileImage: String, googleId: String, organizationDomain: String?): User {
      require(organizationDomain == ORGANIZATION_DOMAIN) { throw OrganizationDomainNotAllowedException() }

      return User(name = name, email = email, profileImage = profileImage, googleId = googleId)
    }
  }

  /**
   * This is a factory method of the payment pin.
   */
  fun setPaymentPin(raw: String) {
    if (!paymentPin.isNullObject()) {
      throw PaymentPinException.PaymentPinAlreadySetException()
    }
    paymentPin = PaymentPin.create(raw)
  }

  /**
   * This is another factory method of the payment pin.
   */
  fun updatePaymentPin(raw: String) {
    if (paymentPin.isNullObject()) {
      throw PaymentPinException.PaymentPinNotSetException()
    }
    paymentPin = PaymentPin.create(raw)
  }

  fun verifyPaymentPin(pin: String) {
    if (pinTryCount >= PIN_TRY_COUNT_LIMIT) {
      throw PaymentPinException.PaymentPinTryCountLimitException()
    }

    if (!paymentPin.verify(pin)) {
      pinTryCount++
      throw PaymentPinException.InvalidPaymentPinException()
    }

    pinTryCount = 0
  }
}