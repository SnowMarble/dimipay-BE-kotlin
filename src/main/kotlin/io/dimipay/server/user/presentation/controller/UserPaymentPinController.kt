package io.dimipay.server.user.presentation.controller

import io.dimipay.server.auth.application.annotation.ValidateJwt
import io.dimipay.server.user.application.security.annotation.CurrentUser
import io.dimipay.server.user.application.security.annotation.UserAuth
import io.dimipay.server.user.application.service.PaymentPinService
import io.dimipay.server.user.domain.model.user.User
import io.dimipay.server.user.presentation.constant.JwtHeaders
import io.dimipay.server.user.presentation.dto.request.PaymentPinRegisterRequestDto
import io.dimipay.server.user.presentation.dto.request.PaymentPinVerifyRequestDto
import io.dimipay.server.user.presentation.dto.response.PaymentPinRegisterResponseDto
import io.dimipay.server.user.presentation.dto.response.PaymentPinVerifyResponseDto
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class UserPaymentPinController(
    private val paymentPinService: PaymentPinService
) {

  @UserAuth(allowOnboarding = true)
  @PostMapping(UserEndpoints.PAYMENT_PIN)
  fun register(
      @CurrentUser user: User,
      @Valid @RequestBody body: PaymentPinRegisterRequestDto
  ): PaymentPinRegisterResponseDto {
    val paymentPinJwt = paymentPinService.register(user, body.pin)
    return PaymentPinRegisterResponseDto(paymentPinJwt)
  }

  @UserAuth
  @PatchMapping(UserEndpoints.PAYMENT_PIN)
  @ValidateJwt(JwtHeaders.PAYMENT_PIN_AUTH)
  fun update(
      @CurrentUser user: User,
      @Valid @RequestBody body: PaymentPinRegisterRequestDto
  ) {
    paymentPinService.update(user, body.pin)
  }

  @UserAuth(allowOnboarding = true)
  @PostMapping(UserEndpoints.PAYMENT_PIN_VERIFY)
  fun verify(
      @CurrentUser user: User,
      @Valid @RequestBody body: PaymentPinVerifyRequestDto
  ): PaymentPinVerifyResponseDto {
    val pinAuthJwt = paymentPinService.verify(user, body.pin)
    return PaymentPinVerifyResponseDto(pinAuthJwt)
  }
}