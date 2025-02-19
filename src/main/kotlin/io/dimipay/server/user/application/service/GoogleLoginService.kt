package io.dimipay.server.user.application.service

interface GoogleLoginService {

  fun login(idTokenString: String): GoogleLoginResult

  class GoogleLoginResult(
      val accessToken: String,
      val isNewUser: Boolean
  )
}

