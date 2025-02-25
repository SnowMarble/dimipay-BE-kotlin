package io.dimipay.server.user.application.service.impl

import io.dimipay.server.auth.application.security.JwtService
import io.dimipay.server.user.application.security.PaymentPinJwtClaims
import io.dimipay.server.user.application.service.PaymentPinService
import io.dimipay.server.user.domain.model.user.PaymentPin
import io.dimipay.server.user.domain.model.user.User
import io.dimipay.server.user.domain.repository.UserRepository
import io.dimipay.server.user.exception.PaymentPinException
import org.springframework.stereotype.Service

@Service
class PaymentPinServiceImpl(
    private val userRepository: UserRepository,
    private val jwtService: JwtService
) : PaymentPinService {

  override fun register(user: User, pin: String): String {
    if (user.paymentPin != null) {
      throw PaymentPinException.PaymentPinAlreadySetException()
    }

    user.paymentPin = PaymentPin.create(pin)

    userRepository.save(user)

    return generatePinAuthJwt(user.id)
  }

  override fun update(user: User, newPin: String) {
    user.paymentPin = PaymentPin.create(newPin)

    userRepository.save(user)
  }

  override fun verify(user: User, pin: String): String {
    return generatePinAuthJwt(user.id)
  }

  private fun generatePinAuthJwt(userId: Long): String {
    val claims = PaymentPinJwtClaims(userId.toString())
    return jwtService.generateToken(claims, PaymentPinService.PAYMENT_PIN_JWT_LIFE)
  }
}