package io.dimipay.server.user.application.service

import io.dimipay.server.user.domain.model.user.User

interface PaymentPinService {

  companion object {

    const val PAYMENT_PIN_JWT_LIFE = 60L
  }

  /**
   * Register payment pin for new user. It'll throw when user already has registered pin.
   * To update pin, you should use [update].
   */
  fun register(user: User, pin: String): String

  /**
   * Updated user's payment pin. If user does not have registered pin, it'll throw.
   * To register pin, you should use [register].
   */
  fun update(user: User, newPin: String)

  /**
   * @return A jwt for endpoint authentication where payment pin verification is needed.
   */
  fun verify(user: User, pin: String): String
}