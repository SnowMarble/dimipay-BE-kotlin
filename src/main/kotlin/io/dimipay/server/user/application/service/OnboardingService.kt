package io.dimipay.server.user.application.service

import io.dimipay.server.common.dto.Tokens
import io.dimipay.server.user.domain.model.user.User

/**
 * Onboarding is to verify the payment pin and update device id and biokey.
 * Note that Onboarding service does not verify the pin. It's done by jwt validation, which is return value of pin registration or verification.
 * So, conceptually, onboarding involves pin validation, but the service does not do it.
 */
interface OnboardingService {

  fun onboarding(user: User, deviceId: String?, biokey: String?): Tokens
}