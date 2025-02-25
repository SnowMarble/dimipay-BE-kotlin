package io.dimipay.server.user.application.service.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

sealed class RefreshTokenException(status: HttpStatus, message: String) : ResponseStatusException(status, message) {

  class RefreshTokenNotFound : RefreshTokenException(HttpStatus.NOT_FOUND, "Refresh token not found")
  
  class RefreshTokenExpired : RefreshTokenException(HttpStatus.UNAUTHORIZED, "Refresh token expired")
}