package io.dimipay.server.auth.application.exception

import io.dimipay.server.common.exception.UnauthorizedException

sealed class JwtValidaeAspectException(message: String) : UnauthorizedException(message) {

  class TokenNotFoundException(header: String) : JwtValidaeAspectException("$header header not found")
  
  class InvalidTokenException : JwtValidaeAspectException("invalid token")
}