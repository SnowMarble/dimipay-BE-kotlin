package io.dimipay.server.user.presentation.controller

object UserEndpoints {

  const val ROOT = "/api/v1/users"

  const val OAUTH2_LOGIN = "$ROOT/oauth2/login"
  const val OAUTH2_AUTHORIZE_GOOGLE = "$ROOT/oauth2/authorize/google"
  const val OAUTH2_CODE_GOOGLE = "$ROOT/oauth2/code/google"
  const val ONBOARDING = "$ROOT/onboarding"
  const val PAYMENT_PIN = "$ROOT/pin"
  const val PAYMENT_PIN_VERIFY = "$ROOT/pin/verify"
}
