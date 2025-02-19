package io.dimipay.server.user.presentation.controller

import io.dimipay.server.auth.application.annotation.ValidateJwt
import io.dimipay.server.user.application.security.PaymentPinJwtClaims
import io.dimipay.server.user.application.security.annotation.AllowOnboarding
import io.dimipay.server.user.application.security.annotation.CurrentUser
import io.dimipay.server.user.application.service.PaymentPinService
import io.dimipay.server.user.domain.model.user.User
import io.dimipay.server.user.presentation.constant.JwtHeaders
import io.dimipay.server.user.presentation.dto.PaymentPinRegisterRequestDto
import io.dimipay.server.user.presentation.dto.PaymentPinRegisterResponseDto
import io.dimipay.server.user.presentation.dto.PaymentPinVerifyRequestDto
import io.dimipay.server.user.presentation.dto.PaymentPinVerifyResponseDto
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class UserPaymentPinController(
    private val paymentPinService: PaymentPinService
) {

  @AllowOnboarding
  @PostMapping(UserEndpoints.PAYMENT_PIN)
  fun register(
      @CurrentUser user: User,
      @Valid @RequestBody body: PaymentPinRegisterRequestDto
  ): PaymentPinRegisterResponseDto {
    val paymentPinJwt = paymentPinService.register(user, body.pin)
    return PaymentPinRegisterResponseDto(paymentPinJwt)
  }

  @PatchMapping(UserEndpoints.PAYMENT_PIN)
  @ValidateJwt(JwtHeaders.PAYMENT_PIN_AUTH, PaymentPinJwtClaims::class)
  fun update(
      @CurrentUser user: User,
      @Valid @RequestBody body: PaymentPinRegisterRequestDto
  ) {
    paymentPinService.update(user, body.pin)
  }

  @PostMapping(UserEndpoints.PAYMENT_PIN_VERIFY)
  fun verify(
      @CurrentUser user: User,
      @Valid @RequestBody body: PaymentPinVerifyRequestDto
  ): PaymentPinVerifyResponseDto {
    val pinAuthJwt = paymentPinService.verify(user, body.pin)
    return PaymentPinVerifyResponseDto(pinAuthJwt)
  }
}