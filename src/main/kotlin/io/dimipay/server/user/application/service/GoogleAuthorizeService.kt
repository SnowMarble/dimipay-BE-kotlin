package io.dimipay.server.user.application.service

import jakarta.servlet.http.HttpServletRequest

interface GoogleAuthorizeService {

  fun getAuthorizationUrl(): String

  fun getIdToken(request: HttpServletRequest): String
}